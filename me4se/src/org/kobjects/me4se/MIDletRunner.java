// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
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

package org.kobjects.me4se;

import java.applet.*;
import java.awt.*;
import javax.microedition.midlet.*;


public class MIDletRunner extends Applet {

    boolean running;
    public static boolean isApplet;

    public void start () {

	if (!running) {
	    isApplet = true;
	    running = true;

	    if (ApplicationManager.manager != null) 
		ApplicationManager.manager.destroy ();
	   
	    new ApplicationManager (this).init (getParameter ("MIDlet"));
	}

	ApplicationManager.manager.start ();
    }

    
    public void stop () {
	ApplicationManager.manager.pause ();
    }



    public void destroy () {
	ApplicationManager.manager.destroy ();
    }


    public static void main (String [] argv)  {
	new ApplicationManager (null).init (argv [0]);
	ApplicationManager.manager.start ();
    }
}



