// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Sebastian Vastag 
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

package javax.microedition.midlet;

import org.me4se.impl.JadFile;
import java.util.*;
import java.io.File;

/** this class is needed *here* in order to be able to call the
    protected MIDlet startApp() method (etc.). It should perhaps be
    hidden from the documentation. */

public class ApplicationManager {

    public static ApplicationManager manager;
    
    public JadFile jad;

    public MIDlet active;
    public java.awt.Container displayContainer; 
    public int canvasWidth;
    public int canvasHeight;
    public Properties properties;


    public ApplicationManager (java.awt.Container dc, Properties properties) {

	this.properties = properties;

	if (manager != null) 
	    manager.destroy ();
	
	manager = this;

	if (dc != null) 
	    displayContainer = dc;
	else if (displayContainer == null) {
	    java.awt.Frame frame = new java.awt.Frame ("MIDlet");
	    displayContainer = frame;

	    frame.setSize 
		(Integer.parseInt (properties.getProperty ("width", "150")), 
		 Integer.parseInt (properties.getProperty ("height", "200")));


	    // not fully supported yet

	    frame.setResizable 
		("|1|true|".indexOf ("|"+properties.getProperty ("resizable")+"|") != -1);
	    /*
	    System.out.println 
		("resizable: "+ 
		 ("|1|true|".indexOf ("|"+properties.getProperty ("resizable")+"|") != -1));
	    */

	    frame.addWindowListener (new java.awt.event.WindowAdapter () {
		    public void windowClosing (java.awt.event.WindowEvent ev) {
			destroy ();
			System.exit (0);
		    }});
	    
	    frame.show ();    
	}

	displayContainer.removeAll ();
	displayContainer.setLayout (new java.awt.BorderLayout ());
	displayContainer.setBackground (java.awt.Color.white);
    }


    /** not called if MIDlet provides a main method */

    public void launch () {
	
	String jadFile = properties.getProperty ("jad");
	String midlet = properties.getProperty ("MIDlet");

	if (jadFile != null) {
	    try {
		jad = new JadFile (jadFile);

		if (midlet == null)
		    midlet = ((String[]) jad.getMIDletList ().elementAt (0)) [2];
	    }
	    catch (Exception e) {
		if (midlet != null)
		    System.err.println ("Error opening JAD file:"+e);
		else 
		    throw new RuntimeException ("Error opening JAD file:"+e);
	    }
	}


	try {
	    //    System.out.println ("name:" +name);
	    active = ((MIDlet) Class.forName (midlet).newInstance ()); 
	    //  System.out.println ("midlez:" +midlet);
	}
	catch (Exception e) {
	    e.printStackTrace ();
	    throw new RuntimeException (e.toString ());
	}	

	setRmsDir ();
    }


    void setRmsDir () {

	if (org.me4se.MIDletRunner.isApplet) { 
	    org.me4se.impl.RecordStoreImpl_file.rmsDir = null;
	    return;
	}

	String name;

	if (jad != null) {
	    name = jad.getName ();
	    int end = name.lastIndexOf ('.');
	    if (end == 1) end = name.length ();
	    int start = Math.max (Math.max 
		(name.lastIndexOf ('/', end),
		 name.lastIndexOf (':', end)),
				  name.lastIndexOf ('\\', end));

	    if (start < 0) start = 0;
	    name = name.substring (start, end);
	}
	else
	    name = active.getClass ().getName ();

	File rmsDir = new File (name, "rms");
	rmsDir.mkdirs ();
	org.me4se.impl.RecordStoreImpl_file.rmsDir = rmsDir;
    }


    public void start () {
	try {
	    if (active != null) 
		active.startApp ();
	}
	catch (MIDletStateChangeException e) {
	}
    }


    public void pause () {
	
	if (active != null) 
	    active.pauseApp ();

    }

    public void destroy () {
	try {
	    if (active != null) 
		active.destroyApp (true);
	}
	catch (MIDletStateChangeException e) {
	}

	active = null;
    }
}
