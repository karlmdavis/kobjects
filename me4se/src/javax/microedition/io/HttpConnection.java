package javax.microedition.io;

import java.io.*;

public interface HttpConnection extends ContentConnection {

    public static final String HEAD = "HEAD";
    public static final String GET = "GET";
    public static final String POST = "POST";


    public long getDate () throws IOException;
    public long getExpiration () throws IOException;
    public String getFile ();
    public String getHeaderField (String key) throws IOException;
    public String getHeaderField (int index) throws IOException;
    public int getHeaderFieldInt (String key, int def) throws IOException;
    public String getHeaderFieldKey (int index) throws IOException;
    public long getHeaderFieldDate (String key, long def) throws IOException;
    public String getHost ();
    public long getLastModified () throws IOException;
    public int getPort ();
    public String getProtocol ();
    public String getQuery ();
    public String getRef ();
    public int getResponseCode () throws IOException;
    public String getRequestProperty (String name);
    public String getURL ();
    public String getRequestMethod ();
    public String getResponseMessage () throws IOException;
    public void setRequestMethod (String method) throws IOException;
    public void setRequestProperty (String key, 
				    String value) throws IOException;

}
