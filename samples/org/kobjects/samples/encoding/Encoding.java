package org.kobjects.samples.encoding;

import java.util.*;
import org.kobjects.isodate.*;
import org.kobjects.base64.*;


public class Encoding {



    public static void main (String [] argv) {
	
	Date date = new Date ();

	String id = IsoDate.dateToString (date, IsoDate.DATE_TIME);
	System.out.println ("ISO Date Now:    "+id);
	Date date2 = IsoDate.stringToDate (id, IsoDate.DATE_TIME);
	String id2 = IsoDate.dateToString (date2, IsoDate.DATE_TIME);
	System.out.println ("After Roundtrip: "+id2);
	
	System.out.println ("Equals:          "+date.equals (date2));

    }



}
