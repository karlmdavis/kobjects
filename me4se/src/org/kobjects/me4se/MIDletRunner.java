// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Sebastian Vastag 
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
import java.util.*;

public class MIDletRunner extends Applet {

    boolean running = false;
    public static boolean isApplet;

    public void start () {
	//        System.out.println("test");

	if (!running) {
	    isApplet = true;
	    running = true;

	    if (ApplicationManager.manager != null) 
		ApplicationManager.manager.destroy ();
            //String[] param = {getParameter ("MIDlet"), getParameter("JAD-File")};

	    Properties param = new Properties ();

	    if (getParameter ("jad") != null) 
		param.put ("jad", getParameter ("jad"));
	    
	    if (getParameter ("jad-file") != null) 
		param.put ("jad", getParameter ("jad-file"));
			       
	    if (getParameter ("MIDlet") != null) 
		param.put ("MIDlet", getParameter ("MIDlet"));
			       
	    new ApplicationManager (this, param).launch ();
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

	Properties param = new Properties ();
	String midlet = null;
	String jadfile = null;
        
	for (int i = 0; i < argv.length; i++) {

	    if (argv [i].startsWith ("-")) {
		param.put (argv [i].substring (1), argv [i+1]);
		i++;
	    }

	    else if (argv [i].indexOf (".jad") == argv [i].length() - 4)
		param.put ("jad", argv[i]);
	    else
		param.put ("MIDlet", argv[i]);
	}

	if (param.get ("MIDlet") == null && param.get ("jad") == null) 
	    System.err.println ("Please specify an .jad-file or a MIDlet class name.");
	else {
	    new ApplicationManager (null, param).launch ();
	    ApplicationManager.manager.start ();
	}
    }
}



