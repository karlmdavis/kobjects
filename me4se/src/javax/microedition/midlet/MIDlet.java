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

import javax.microedition.lcdui.*;
import org.kobjects.me4se.MIDletRunner;

public abstract class MIDlet {

    
    public MIDlet () {
	if (ApplicationManager.manager == null) 
	    new ApplicationManager (null);

	ApplicationManager.manager.active = this;
	
	Display d = Display.getDisplay (this);
	Canvas c = new Canvas () {
		public void paint (Graphics g) {
		   
		}
	    };
	
	d.setCurrent (c);
	c.getWidth ();
	c.getHeight ();
	//	d.setCurrent (null);
    } 
    
    protected abstract void destroyApp (boolean unconditional) throws MIDletStateChangeException;


    // what about the manifest...?
    
    public String getAppProperty (String key) {

	return ApplicationManager.manager.jad.getProperty (key);
	/*
	if (key.equals ("microedition.configuration"))
	    return "CLDC 1.0";
        if (key.equals ("microedition.profiles")) 
	    return "MIDP 1.0";

        if (key.equals ("microedition.encoding"))
	    return "UTF-8";
	*/

    }
    

    public void notifyDestroyed () {
	if (!(ApplicationManager.manager.displayContainer 
	      instanceof java.applet.Applet))
	    System.exit (0);
	
	Display.getDisplay (this).setCurrent (null);

	if (ApplicationManager.manager.active == this)
	    ApplicationManager.manager.active = null;
    }


    public void notifyPaused () {
    }


    protected abstract void pauseApp ();


    public void resumeRequest () {
	/*try {
	    startApp ();
	}
	catch (MIDletStateChangeException e) {
	} */
    }
    

    protected abstract void startApp () throws MIDletStateChangeException;
    

}
