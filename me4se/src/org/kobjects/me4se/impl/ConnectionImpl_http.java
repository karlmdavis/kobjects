package org.kobjects.me4se.impl;
        
import java.io.*;
import java.net.*;
import javax.microedition.io.*;
             

public class ConnectionImpl_http extends ConnectionImpl 
    implements HttpConnection {

    URL url;
    HttpURLConnection httpUrlConnection;
    

    public void open (String url, int mode, 
		      boolean timeouts) throws IOException {

	this.url = new URL (url);
	httpUrlConnection = (HttpURLConnection) this.url.openConnection ();

	httpUrlConnection.setDoOutput ((mode & Connector.WRITE) != 0);
	httpUrlConnection.setDoInput ((mode & Connector.READ) != 0);
    }

    
    public void setRequestMethod (String method) throws IOException {
	httpUrlConnection.setRequestMethod (method);
	
    }


    public void setRequestProperty (String key, String value) throws IOException {
	httpUrlConnection.setRequestProperty (key, value);
    }
    

    public InputStream openInputStream () throws IOException {
	return httpUrlConnection.getInputStream ();
    }

    public DataInputStream openDataInputStream () throws IOException {
	return new DataInputStream (openInputStream ());
    }


    public OutputStream openOutputStream () throws IOException {
	return httpUrlConnection.getOutputStream ();
    }

    public DataOutputStream openDataOutputStream () throws IOException {
	return new DataOutputStream (openOutputStream ());
    }


    public void close () {
	httpUrlConnection.disconnect ();
	//getOutputStream ().close ();
	//httpUrlConnection.getInputStream ().close ();
    }

    public long getLength ()  {
	return httpUrlConnection.getContentLength ();
    }

    public String getType () {
	return httpUrlConnection.getContentType ();
    }

    public String getEncoding () {
	return httpUrlConnection.getContentEncoding ();
    }

    public int getResponseCode () throws IOException {
	return httpUrlConnection.getResponseCode ();
    }
    
}
