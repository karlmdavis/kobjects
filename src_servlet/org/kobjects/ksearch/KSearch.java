package org.kobjects.ksearch;

import javax.servlet.*;
import javax.servlet.http.*;
import java.net.*;
import java.util.*;
import java.io.*;
import org.kobjects.xml.XmlReader;

// (C) 2002 by Stefan Haustein 
// Rolandstrasse 27, D-46045 Oberhausen, Germany
// All rights reserved.
//
// For licensing details, please refer to the file "license.txt",
// distributed with this file.

/**
 * @author Stefan Haustein */

public class KSearch extends HttpServlet implements Runnable {

    /**
     * Constructor for KSearch.
     */

    Integer[] INTS = new Integer[1024];

    TreeMap errors = new TreeMap();

    Object O = new Object();
    Map words = new TreeMap();
    Map wordsOperational = null;
    Set pages = new HashSet();
    String root;

    public KSearch() {
        for (int i = 0; i < INTS.length; i++)
            INTS[i] = new Integer(i);
    }

    public void init(ServletConfig config) {
        root = config.getInitParameter("root");
        new Thread(this).start();
    }

    public void run() {
        examine(root, "N/A", 10);
        wordsOperational = words;
    }

    public void addWord(String word, String url) {

        if (word.length() < 3)
            return;

        word = word.toLowerCase();
        int len = word.length();

        Map wordTable = (Map) words.get(word);

        Integer old;
        if (wordTable == null) {
            wordTable = new TreeMap();
            words.put(word, wordTable);
            old = null;
        }
        else
            old = (Integer) wordTable.get(url);

        wordTable.put(
            url,
            old == null
                ? INTS[1]
                : INTS[Math.min(
                    old.intValue() + 1,
                    INTS.length - 1)]);
    }

    public void examine(String url, String referrer, int ttl) {

        try {

            if (ttl == 0)
                return;
            ttl--;

            int hash = url.indexOf('#');
            if (hash != -1)
                url = url.substring(0, hash);

            if (!url.startsWith(root))
                return;

            if (url.endsWith("/"))
                url = url + "index.html";

            if (pages.contains(url))
                return;

            url.intern();
            pages.add(url);

            System.out.println("examining: " + url);

            URLConnection con = new URL(url).openConnection();

            if (!con
                .getContentType()
                .equalsIgnoreCase("text/html")) {
                System.out.println(
                    " - unknown content type: "
                        + con.getContentType());
                return;
            }

            String enc = con.getContentEncoding();

            Reader r =
                enc == null
                    ? new InputStreamReader(con.getInputStream())
                    : new InputStreamReader(
                        con.getInputStream(),
                        enc);

            XmlReader xr = new XmlReader(r);
            xr.relaxed = true;
            xr.defineCharacterEntity("auml", "ae");
            xr.defineCharacterEntity("ouml", "oe");
            xr.defineCharacterEntity("uuml", "ue");
            xr.defineCharacterEntity("Auml", "ae");
            xr.defineCharacterEntity("Ouml", "oe");
            xr.defineCharacterEntity("Uuml", "ue");
            xr.defineCharacterEntity("nbsp", " ");
            xr.defineCharacterEntity("szling", "ss");

            while (xr.next() != XmlReader.END_DOCUMENT) {
                if (xr.getType() == XmlReader.START_TAG
                    && xr.getName().toLowerCase().equals("a")) {

                    String href = xr.getAttributeValue("href");
                    if (href == null)
                        href = xr.getAttributeValue("HREF");

                    if (href != null) {

                        if (href.startsWith("http://")) {
                            examine(href, url, ttl);
                        }
                        else if (
                            !href.startsWith("#")
                                && !href.startsWith("mailto:")
                                && !href.startsWith("ftp:")
                                && href.indexOf("..") == -1) {

                            while (href.startsWith("./"))
                                href = href.substring(2);

                            while (true) {
                                int cut = href.indexOf("/./");
                                if (cut == -1)
                                    break;
                                href =
                                    href.substring(0, cut)
                                        + href.substring(cut + 2);
                            }

                            if (href.startsWith("/")) {
                                int cut = url.indexOf('/', 7);
                                examine(
                                    cut == -1
                                        ? url + href
                                        : url.substring(cut)
                                            + href,
                                    url,
                                    ttl);
                            }
                            else {
                                int cut = url.lastIndexOf('/');

                                if (cut < 7)
                                    examine(
                                        url + "/" + href,
                                        url,
                                        ttl);
                                else
                                    examine(
                                        url.substring(
                                            0,
                                            cut + 1)
                                            + href,
                                        url,
                                        ttl);
                            }
                        }
                    }
                }
                else if (xr.getType() == XmlReader.TEXT) {
                    String s = xr.getText();
                    int len = s.length();
                    int i0 = 0;
                    while (i0 < len) {

                        while (i0 < len
                            && !Character.isLetter(s.charAt(i0))
                            && !Character.isDigit(s.charAt(i0)))
                            i0++;

                        int i = i0;

                        while (i < len
                            && (Character.isLetter(s.charAt(i))
                                || Character.isDigit(s.charAt(i))))
                            i++;

                        if (i - i0 > 3) {
                            addWord(
                                s.substring(i0, i).toLowerCase(),
                                url);
                        }

                        i0 = i;
                    }
                }
            }

            r.close();
        }
        catch (FileNotFoundException e) {
            System.err.println(e.toString());
            error(referrer, e);
        }
        catch (Exception e) {
            e.printStackTrace();
            error(url, e);
        }
    }

    void error(String url, Exception e) {
        Vector list = (Vector) errors.get(url);
        if (list == null) {
            list = new Vector();
            errors.put(url, list);
        }

        list.addElement(e);
    }

    void writeLog(Writer writer) throws IOException {

        if (errors.size() == 0)
            return;

        writer.write(
            "<p><b><font color='red'>This is a local request. Please find"
                + " a list of broken (local) links below.</font></b></p>");

        for (Iterator i = errors.keySet().iterator();
            i.hasNext();
            ) {
            String url = (String) i.next();
            writer.write(
                "<a href='" + url + "'>" + url + "</a><br />");
            Vector v = (Vector) errors.get(url);
            writer.write("<ul>");
            for (int j = 0; j < v.size(); j++) {
                writer.write(
                    "<li>" + v.elementAt(j) + "</li>\r\n");
            }
            writer.write("</ul>");
        }
    }

    public void doGet(
        HttpServletRequest request,
        HttpServletResponse response)
        throws IOException {

        Map map = request.getParameterMap();

        response.setContentType("text/html");
        Writer writer = response.getWriter();

        writer.write("<html>");

        String path = request.getPathInfo();

        if (path.endsWith("log"))
            writeLog(writer);
        else {

            String[] q = (String[]) map.get("q");

            String src = q.length == 0 ? "" : q[0];

            writer.write(
                "<head><title>Search results for '"
                    + src
                    + "'</title></head><body>");

            if (wordsOperational == null) {
                writer.write(
                    "<p><font color='red'>Harvesting process not yet finished (currently processed "
                        + pages.size()
                        + " pages); please check back in a few minutes!</font></p>");
            }
            else {
                Map pages = (Map) wordsOperational.get(src);
                if (pages == null)
                    pages = new TreeMap();

                writer.write(
                    "<h3>Found "
                        + pages.size()
                        + " Occurences of '"
                        + src
                        + "':</h3><ul>");

                for (Iterator i = pages.keySet().iterator();
                    i.hasNext();
                    ) {
                    String url = (String) i.next();
                    writer.write(
                        "<li><a href='"
                            + url
                            + "'>"
                            + url.substring(root.length())
                            + "</a> ("
                            + ((Integer) pages.get(url))
                                .intValue()
                            + " occurences)</li>\r\n");
                }
                writer.write("</ul>");
            }
        }

        String remoteIP = request.getRemoteAddr();
        int cut = remoteIP.lastIndexOf('.');
        if (InetAddress
            .getLocalHost()
            .getHostAddress()
            .startsWith(remoteIP.substring(0, cut + 1))) {
            writeLog(writer);
        }

        writer.write("</body>");
        writer.write("</html>");
        writer.flush();

    }

    /*
        public static void main(String[] argv) {
            KSearch s = new KSearch();
            s.root = "http://www-ai.cs.uni-dortmund.de";
            s.examine(s.root, 10);
    
            for (Enumeration e = s.words.keys();
                e.hasMoreElements();
                ) {
                String word = (String) e.nextElement();
                System.out.println(word);
                Hashtable pages = (Hashtable) s.words.get(word);
                for (Enumeration f = pages.keys();
                    f.hasMoreElements();
                    ) {
                    String url = (String) f.nextElement();
                    System.out.println(
                        " - " + pages.get(url) + " x on " + url);
    
                }
            }
        }*/
}
