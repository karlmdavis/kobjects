package javax.microedition.io;

import java.io.*;

public interface HttpConnection extends ContentConnection {

    public static final String HEAD = "HEAD";
    public static final String GET = "GET";
    public static final String POST = "POST";


    public int getResponseCode () throws IOException;

    public void setRequestProperty (String key, 
				    String value) throws IOException;

    public void setRequestMethod (String method) throws IOException;

}
