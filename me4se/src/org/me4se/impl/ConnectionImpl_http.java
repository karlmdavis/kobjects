// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API Complete
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.


// Andre, this is the old version; I have shifted your version
// to src_applet in order to be able to build a "simple" version
// for execution on PJava devices. 

package org.me4se.impl;
        
import java.io.*;
import java.net.*;
import javax.microedition.io.*;
             

public class ConnectionImpl_http extends ConnectionImpl 
    implements HttpConnection {

    URL url;
    HttpURLConnection con;


    public void open (String url, int mode, 
		      boolean timeouts) throws IOException {

	this.url = new URL (url);
	con = (HttpURLConnection) this.url.openConnection ();

	con.setUseCaches (false);
	con.setDoOutput ((mode & Connector.WRITE) != 0);
	con.setDoInput ((mode & Connector.READ) != 0);
	con.setRequestProperty ("connection", "close");
    }


    public long getDate () throws IOException {
	return con.getDate ();
    }

    public String getEncoding () {
	return con.getContentEncoding ();
    }

    public long getExpiration () throws IOException {
	return con.getExpiration ();
    }

    public String getFile () {
	return url.getFile ();
    }

    public String getHeaderField (String key) throws IOException {
	return con.getHeaderField (key);
    }

    public String getHeaderField (int index) throws IOException {
	return con.getHeaderField (index);
    }

    public int getHeaderFieldInt (String key, int def) throws IOException {
	return con.getHeaderFieldInt (key, def);
    }
    
    public String getHeaderFieldKey (int index) throws IOException {
	return con.getHeaderFieldKey (index);
    }

    public long getHeaderFieldDate (String key, long def) throws IOException {
	return con.getHeaderFieldDate (key, def);
    }


    public String getHost () {
	return url.getHost ();
    }

    public long getLength ()  {
	return con.getContentLength ();
    }

    public long getLastModified () throws IOException {
	return con.getLastModified ();
    }


    public int getPort () {
	return url.getPort ();
    }

    public String getProtocol () {
	return url.getProtocol ();
    }


    public String getQuery () {
	return url.getQuery ();
    }

    public String getRef () {
	return url.getRef ();
    }

    public int getResponseCode () throws IOException {
	return con.getResponseCode ();
    }

    public String getRequestProperty (String name) {
	return con.getRequestProperty (name);
    }

    public String getType () {
	return con.getContentType ();
    }

    public String getURL () {
	return url.toString ();
    }

    public String getRequestMethod () {
	return con.getRequestMethod ();
    }


    public String getResponseMessage () throws IOException {
	return con.getResponseMessage ();
    }



    public void setRequestMethod (String method) throws IOException {
	con.setRequestMethod (method);
    }


    public void setRequestProperty (String key, 
				    String value) throws IOException {
	con.setRequestProperty (key, value);
    }


    
    public InputStream openInputStream () throws IOException {
	//	con.getOutputStream ().flush ();
	
	try {
	    return con.getInputStream ();
	}
	catch (IOException e) {
	    InputStream is = con.getErrorStream ();
	    if (is == null) throw e;
	    return is;
	}
    }

    public DataInputStream openDataInputStream () throws IOException {
	return new DataInputStream (openInputStream ());
    }


    public OutputStream openOutputStream () throws IOException {
	return con.getOutputStream ();
    }

    public DataOutputStream openDataOutputStream () throws IOException {
	return new DataOutputStream (openOutputStream ());
    }


    public void close () {
	con.disconnect ();
	//getOutputStream ().close ();
	//con.getInputStream ().close ();
    }

}
