package org.me4se.impl;

import javax.microedition.io.*;
import javax.microedition.midlet.*;
import java.io.*;
import java.net.*;


public class ConnectionImpl_socket extends ConnectionImpl 
    implements StreamConnection {

	public static String socketProxyHost = null;
	public static int socketProxyPort = -1;
	
    Socket socket;

    public void open (String url, int mode, 
		      boolean timeouts) throws IOException {
	// socket://

	int cut = url.lastIndexOf (':');

// added by andre
	
//	socket = new Socket 
//	    (url.substring (9, cut), 
//	     Integer.parseInt (url.substring (cut+1)));
	String host;
	int port;

	if( cut >= 9 )
	{
		host = url.substring (9, cut);
		port = Integer.parseInt (url.substring (cut+1));
	}
	else
	{
		host = url.substring (9);
		port = 80;
	}
	
	if( socketProxyHost == null )
	{
	socket = new Socket 
		    ( 	host , 
		     	port );
	}
	else
	{
		socket = new Socket 
		    ( 	socketProxyHost , 
		     	socketProxyPort );
		BufferedOutputStream bos = new BufferedOutputStream( socket.getOutputStream() );
		DataOutputStream dos = new DataOutputStream( bos );
		dos.writeUTF( host );
		dos.writeInt( port );
		dos.flush();
	}	
// end added by andre

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
