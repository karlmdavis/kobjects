package org.kobjects.util;


import java.util.Vector;

public class Csv {


    public static String [] decode (String line) {
	Vector tmp = new Vector ();

	int p0 = 0;
	int len = line.length ();

	System.out.println (line);

	while (true) {
	    // skip spaces
	   
	    while (p0 < len && line.charAt (p0) <= ' ') p0++;
	    if (p0 >= len) break;
	    
	    if (line.charAt (p0) == '"') { 
		p0++;
		// copy this to the mime decoder!
		StringBuffer buf = new StringBuffer ();
		while (true) {
		    char c = line.charAt (p0++);
		    if (c == '"') { 
			if (p0 == len || line.charAt (p0) != '"') break;
			p0++;
		    }
		    buf.append (c);
		}

		tmp.addElement (buf.toString ());

		while (p0 < len && line.charAt (p0) <= ' ') p0++;
		if (p0 >= len) break;
		else if (line.charAt (p0) != ',') 
		    throw new RuntimeException ("Comma expected at "+p0+ " line: "+line);
		p0++;
	    }
	    else {
		int p1 = line.indexOf (',', p0);
		if (p1 == -1) {
		    tmp.addElement (line.substring (p0).trim ());
		    break;
		}
		tmp.addElement (line.substring (p0, p1).trim ());
		p0 = p1+1;
	    }
	}

	String [] result = new String[tmp.size ()];
	for (int i = 0; i < result.length; i++) 
	    result [i] = (String) tmp.elementAt (i);

	return result;
    } 
}
