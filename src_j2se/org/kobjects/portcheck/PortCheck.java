package org.kobjects.portcheck;

import java.net.*;
import java.io.*;

public class PortCheck {


    public static void main (String[] argv) {

	try {
	    Socket s = new Socket ("localhost", Integer.parseInt (argv[0])); 
	    s.close ();
	}
	catch (IOException e) {
	    System.out.println ("Was not able to connect, returning 0 for restart");
	    System.exit (0);
	}

	System.exit (255);
    }
}
