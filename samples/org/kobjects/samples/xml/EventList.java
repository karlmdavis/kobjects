package org.kobjects.samples.xml;
import java.io.*;

import org.kobjects.xml.*;


public class EventList {

    public static void main (String [] args) throws IOException {
	
	XmlReader xr = new XmlReader 
	    (new FileReader (args.length == 0 ? "sample.xml" : args [0]));
	
	while (xr.next () != XmlReader.END_DOCUMENT) {
	    System.out.println (xr.getPositionDescription ());
	}
	    
    }

}





