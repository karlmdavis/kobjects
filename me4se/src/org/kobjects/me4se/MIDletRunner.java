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



