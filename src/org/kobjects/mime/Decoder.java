package org.kobjects.mime;

import java.io.*;
import java.util.*;
import org.kobjects.base64.*;


public class Decoder {

    InputStream is;
    Hashtable header;
    byte [] binary;
    String text;
    boolean eof;
    String boundary;
    //byte [] buf = new byte [128];
    //int bufCount;
    //int bufPos;

    // add some kind of buffering here!!!
    private final String readLine () {
        
        StringBuffer result = new StringBuffer ();
        while (true) {
            int i = is.read ();
            if (i == -1 && result.length () == 0) return null;
            else if (i == -1 || i == '\n') return result.toString ();
            else if (i != '\r') result.append ((char) i);
        }
    }


    public Decoder (InputStream is, String boundary) throws IOException {

        this.is = is;

        StringBuffer buf = new StringBuffer ();

        String line = null;
        while (true) {
            line = readLine ();
            if (line == null) throw new IOException ("Unexpected EOF");
            if (line.startsWith (boundary)) break;
            buf.append (line);
        }

        text = buf.toString ();
        if (line.endsWith ("--")) eof = true;
    }


    public String getText () {
        return text;
    }

    public byte [] getBinary () {
        return binary;
    }

    
    public String getHeader (String key) {
        return (String) header.get (key.toLowerCase ());
    }

    
    public boolean next () throws IOException {

        if (eof) return false;

	// read header 

	header = new Hashtable ();
        String line;

	while (true) {
	    line = readLine ();
	    if (line == null || line.equals ("")) break;
	    int cut = line.indexOf (':');
	    if (cut == -1) 
		throw new IOException 
		    ("colon missing in multipart header line: "+line);

	    header.put (line.substring (0, cut).trim ().toLowerCase (),
			line.substring (cut+1).trim ());
	}

	if ("base64".equals (getHeader ("Content-Transfer-Encoding"))) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream ();
            while (true) {
                line = readLine ();
                if (line == null) throw new IOException ("Unexpected EOF");
                if (line.startsWith (boundary)) break;

                Base64.decode (line, bos);
            }

            text = null;
            binary = bos.toByteArray ();
	}
	else {
	    StringBuffer buf = new StringBuffer ();

            while (true) {
                line = reader.readLine ();
                if (line == null) throw new IOException ("Unexpected EOF");
                if (line.startsWith (boundary)) break;
                
                buf.append (line);
            }

            text = buf.toString ();
            binary = null;
	}


        if (line.ensWith ("--")) 
            eof = true;

        return true;
    }
}
