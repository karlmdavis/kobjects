// provided by Andreas Merkle 

package org.kobjects.samples.rmsindex;

import javax.microedition.rms.*;
import org.kobjects.rms.RmsIndex;

class RMSIndexTest {

    static final int COUNT = 9;


    public static void main ( String [] argv ) throws RecordStoreException {

	String line = "";
	RmsIndex ri = null;
	
	ri = new RmsIndex( "idxtest_9" );
	
	System.out.println ("Sequential write");
	
	for (int element = 0; element < COUNT; element++ ) {    	
	    String sKey = "Key " + element;
	    String data = "Element " + element;
	    
	    ri.put(sKey, data);
	}
	
	System.out.println ("Sequential read");

	for (int element = 0; element < COUNT; element++ ) {    	
	    String sKey = "Key " + element;
	    		
	    String data=ri.get(sKey);
	    System.out.println(data);
	}

	
	ri.close ();
    }
}


