/*
 * JAD_Parser.java
 *
 * Created on 6. August 2001, 09:55
 */

package org.me4se.impl;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.microedition.io.*;

/**
 *
 * @author  Sebastian Vastag, Øystein Myhre
 * @version 
 */

public class JadFile  {

    private Hashtable properties = new Hashtable();
    String name;

    /** Creates new JadFile */

    public JadFile (String name) throws IOException {

	this.name = name;
	BufferedReader reader = null;

	try {
	    if (name.startsWith("http://")) {
		URL url = new URL( name );
		reader = new BufferedReader
		    (new InputStreamReader 
			(url.openConnection().getInputStream()));
		}
		else {
		    reader = new BufferedReader (new FileReader (name));
		}
	    
	    while (true) {
		String line = reader.readLine ();
		if (line == null) break;
		int i = line.indexOf (':');
	    
		if (i != -1)
		    properties.put (line.substring (0, i).trim (),
				    line.substring (i+1).trim ());
	    }
	}
	finally {
	    if (reader != null)
		try {
		    reader.close (); 
		}
		catch( Exception e ){}
	}
    }
    
    
    public String getProperty (String key) {
	
	return (String) properties.get (key);
    }

    
    /**
     *@return an Array of Arrays (String) containing [0] Name, [1] Icon, [2] Class
     */


    public Vector getMIDletList() { 
	Vector midletlist = new Vector();
	// [0] Name, [1] Icon, [2] Class

	for (int i=1; true; i++) { 
	    String[] midlets={"","",""};

	    String temp=getProperty ("MIDlet-"+i);
	    if (temp==null) break;
	    StringTokenizer tokenizer = new StringTokenizer(temp,",",true);
	    int x=0;
	    
	    while(tokenizer.hasMoreTokens())
		{ String token=((String)tokenizer.nextToken()).trim();
		if(token.equals(",")) x++; else
		    midlets[x]=token;
		}
	    midletlist.addElement(midlets);
	}
	
        return midletlist;
    }
    
    
    public String getName () {
	return name;
    }


    /*
    //For testing only...
    public static void main(String args[]) {
        JAD_Parser jadparser = new JAD_Parser(args[0]);
	}*/
}
