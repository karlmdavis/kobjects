package javax.microedition.midlet;

import java.util.Vector;

public class ApplicationManager {

    public static ApplicationManager manager;
    
    public JAD_Parser jad;

    public MIDlet active;
    public java.awt.Container displayContainer; 
    public int canvasWidth;
    public int canvasHeight;

    public ApplicationManager (java.awt.Container dc) {

	if (manager != null) 
	    manager.destroy ();
	
	manager = this;

	if (dc != null) 
	    displayContainer = dc;
	else if (displayContainer == null) {
	    java.awt.Frame frame = new java.awt.Frame ("MIDlet");
	    displayContainer = frame;
	    frame.setSize (150, 200);
	    frame.setResizable (false);
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


    public void init (String[] args) {
        jad = new JAD_Parser(args[0]);
        Object[] midletlist = jad.getMIDletList();
        String[] firstmidlet = (String[]) midletlist[0];
	try {
	    //    System.out.println ("name:" +name);
	    active = ((MIDlet) Class.forName (firstmidlet[2]).newInstance ()); 
	    //  System.out.println ("midlez:" +midlet);
	}
	catch (Exception e) {
	    e.printStackTrace ();
	    throw new RuntimeException (e.toString ());
	}	
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
