// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete
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
import org.kobjects.me4se.impl.*;

public class Connector {


    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final int READ_WRITE = READ | WRITE;


    public static Connection open (String name) {
	return open (name, READ_WRITE, false);
    }

    public static Connection open (String name, int mode) {
	return open (name, mode, false);
    }


    public static Connection open (String url, int mode, boolean timeOuts) {

	int cut = url.indexOf (':');
	if (cut == -1) throw new RuntimeException (": not found!");

	String protocol = url.substring (0, cut).toLowerCase ();

	try {
	    ConnectionImpl connection = (ConnectionImpl)
		Class.forName ("org.kobjects.me4se.impl.ConnectionImpl_"+protocol).newInstance ();
	    
	    connection.open (url, mode, timeOuts);
	    return connection;
	}
	catch (Exception e) {
	    throw new RuntimeException (e.toString ());
	}
    }


    public static InputStream openInputStream (String name) throws IOException {
	return ((InputConnection) open (name)).openInputStream ();
    }

    public static OutputStream openOutputStream (String name) throws IOException {
	return ((OutputConnection) open (name)).openOutputStream ();
    }
    
    public static DataInputStream openDataInputStream (String name) throws IOException {
	return ((InputConnection) open (name)).openDataInputStream ();
    }

    public static OutputStream openDataOutputStream (String name) throws IOException {
	return ((OutputConnection) open (name)).openDataOutputStream ();
    }


}


    
