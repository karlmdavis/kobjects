// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Geoff Hubbard
//
// STATUS: 
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

package javax.microedition.io;

import java.io.*;

public interface HttpConnection extends ContentConnection {

    public static final String HEAD = "HEAD";
    public static final String GET = "GET";
    public static final String POST = "POST";
    
    public static final int HTTP_OK = 200;


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
