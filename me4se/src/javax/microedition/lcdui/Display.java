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

package javax.microedition.lcdui;

import javax.microedition.midlet.*;
import java.util.*;
import java.awt.event.*;
import org.kobjects.me4se.*;

public class Display {

    static Hashtable midlets = new Hashtable ();
    
    MIDlet midlet;
    Displayable current;
    Vector callSerially = new Vector ();


    Display (MIDlet midlet) {
	this.midlet = midlet;
    }

 

    public void callSerially (Runnable r) {
	callSerially.addElement (r);
	if (current != null) 
	    current.panel.repaint ();
    }

    
    public static Display getDisplay (MIDlet midlet) {

	Display display = (Display) midlets.get (midlet);
	if (display == null) {
	    display = new Display (midlet);
	    midlets.put (midlet, display);
	}
	return display;
    }


    public void setCurrent (Displayable d) {

	if (ApplicationManager.manager.active != midlet) 
	    return;

	ApplicationManager.manager.displayContainer.removeAll ();

	if (current instanceof Canvas) 
	    ((Canvas) current).hideNotify (); 

	if (d == null) 
	    current = null;
	else {
	    if (d instanceof Alert) {
		Alert alert = (Alert) d; 
		if (alert.next == null) alert.next = current;
	    }
	    else if (d instanceof Canvas)
		((Canvas) d).showNotify ();

	    d.display = this;
	    current = d;
	    ApplicationManager.manager.displayContainer.add 
		("Center", d.panel);
	    d.notifyDisplayed ();
	}
	ApplicationManager.manager.displayContainer.validate ();
	ApplicationManager.manager.displayContainer.repaint ();
    }

    
    public void setCurrent (Alert alert, Displayable next) {
	alert.next = next;
	setCurrent (alert);
    }


    public boolean isColor () {
	return true;
    }

    public int numColors () {
	return 1 << (java.awt.Toolkit.getDefaultToolkit ().getColorModel ().getPixelSize () - 1);
    }
}
