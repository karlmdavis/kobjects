package org.kobjects.mime;

import java.io.*;
import java.util.*;
import org.kobjects.base64.*;

public class Decoder {

    InputStream is;
    Hashtable header;
    byte[] data;
    boolean eof;
    String boundary;
    //byte [] buf = new byte [128];
    //int bufCount;
    //int bufPos;

    // add some kind of buffering here!!!
    private final String readLine() throws IOException {

        StringBuffer result = new StringBuffer();
        while (true) {
            int i = is.read();
            if (i == -1 && result.length() == 0)
                return null;
            else if (i == -1 || i == '\n')
                return result.toString();
            else if (i != '\r')
                result.append((char) i);
        }
    }

    /** The "main" element is returned in the hashtable with an empty key ("") */

    public static Hashtable getHeaderElements(String header) {

        String key = "";
        int pos = 0;
        Hashtable result = new Hashtable();
        int len = header.length();

        while (true) {
            // skip spaces

            while (pos < len && header.charAt(pos) <= ' ')
                pos++;
            if (pos >= len)
                break;

            if (header.charAt(pos) == '"') {
                pos++;
                int cut = header.indexOf('"', pos);
                if (cut == -1)
                    throw new RuntimeException("End quote expected in " + header);

                result.put(key, header.substring(pos, cut));
                pos = cut + 2;

                if (pos >= len)
                    break;
                if (header.charAt(pos - 1) != ';')
                    throw new RuntimeException("; expected in " + header);
            }
            else {
                int cut = header.indexOf(';', pos);
                if (cut == -1) {
                    result.put(key, header.substring(pos));
                    break;
                }
                result.put(key, header.substring(pos, cut));
                pos = cut + 1;
            }

            int cut = header.indexOf('=', pos);

            if (cut == -1)
                break;

            key = header.substring(pos, cut).toLowerCase().trim();
            pos = cut + 1;
        }
        return result;
    }

    public Decoder(InputStream is, String _bound) throws IOException {

        this.is = is;
        this.boundary = "--" + _bound;

        StringBuffer buf = new StringBuffer();

        String line = null;
        while (true) {
            line = readLine();
            if (line == null)
                throw new IOException("Unexpected EOF");

            System.out.println("line:  '" + line + "'");
            System.out.println("bound: '" + boundary + "'");

            if (line.startsWith(boundary))
                break;
            buf.append(line);
        }

        data = buf.toString().getBytes();
        if (line.endsWith("--"))
            eof = true;
    }

    public String getText() {
        // should try to use content-type before default here
        return new String(data);
    }

    public byte[] getData() {
        return data;
    }

    public Enumeration getHeaderNames() {
        return header.keys();
    }

    public String getHeader(String key) {
        return (String) header.get(key.toLowerCase());
    }

    public boolean next() throws IOException {

        if (eof)
            return false;

        // read header 

        header = new Hashtable();
        String line;

        while (true) {
            line = readLine();
            if (line == null || line.equals(""))
                break;
            int cut = line.indexOf(':');
            if (cut == -1)
                throw new IOException("colon missing in multipart header line: " + line);

            header.put(
                line.substring(0, cut).trim().toLowerCase(),
                line.substring(cut + 1).trim());

            System.out.println(
                "key: '"
                    + line.substring(0, cut).trim().toLowerCase()
                    + "' value: '"
                    + line.substring(cut + 1).trim());

        }

        System.out.println("cte-head: " + getHeader("Content-Transfer-Encoding"));

        String contentType = getHeader("Content-Type");

        if ("base64".equals(getHeader("Content-Transfer-Encoding"))) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while (true) {
                line = readLine();
                if (line == null)
                    throw new IOException("Unexpected EOF");
                if (line.startsWith(boundary))
                    break;

                Base64.decode(line, bos);
            }

            data = bos.toByteArray();
        }
        else {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            String deli = "\r\n" + boundary;
            int match = 0;

            while (true) {
                int i = is.read();
/*                if (i >= 32 && i <= 127)
                    System.out.print((char) i);
                else
                    System.out.print("#" + i + ";"); */
                if (i == -1)
                    throw new RuntimeException("Unexpected EOF");

                if (((char) i) == deli.charAt(match)) {
                    match++;
                    if (match == deli.length())
                        break;
                }
                else {
                    if (match > 0) {
                        for (int j = 0; j < match; j++)
                            bos.write((byte) deli.charAt(j));

                        match = ((char) i == deli.charAt(0)) ? 1 : 0;
                    }
                    if (match == 0) bos.write((byte) i);
                }
            }

            data = bos.toByteArray();
            line = readLine(); // read crlf and possibly remaining --
        }

        if (line.endsWith("--"))
            eof = true;

        return true;
    }
}