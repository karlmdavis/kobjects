package org.kobjects.me4se.impl;

import javax.microedition.io.*;
import java.io.*;
import java.net.*;


public class ConnectionImpl_socket extends ConnectionImpl 
    implements StreamConnection {

    Socket socket;

    public void open (String url, int mode, 
		      boolean timeouts) throws IOException {
	// socket://

	int cut = url.lastIndexOf (':');

	socket = new Socket 
	    (url.substring (9, cut), 
	     Integer.parseInt (url.substring (cut+1)));
    }


    public InputStream openInputStream () throws IOException {
	return socket.getInputStream ();
    }

    public DataInputStream openDataInputStream () throws IOException {
	return new DataInputStream (openInputStream ());
    }


    public OutputStream openOutputStream () throws IOException {
	return socket.getOutputStream ();
    }

    public DataOutputStream openDataOutputStream () throws IOException {
	return new DataOutputStream (openOutputStream ());
    }

    public void close () throws IOException {
	socket.close ();
    }
    
}
