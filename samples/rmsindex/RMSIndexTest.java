// provided by Andreas Merkle 

import javax.microedition.io.*;
import java.io.*;
import java.util.*;
import javax.microedition.rms.*;
import org.kobjects.rms.RmsIndex;

class RMSIndexTest
{

	public RMSIndexTest()
	{
	
	}
	
	public static void main ( String [] argv ) 
	{
		String line = "";
		RmsIndex ri = null;

		try
	   	{
      			ri = new RmsIndex( "idxtest_9" );
      		}catch( RecordStoreException e ){ System.out.println( "ERROR #1" ); }

      		for( int element = 0; element < 9; element++ ) 
        	{    	
        		String sKey = "Key " + element;
	    		String data = "Element " + element;
	    		
        		try
        		{
	 		  ri.put(sKey, data);
       			}catch( RecordStoreException e ){ System.out.println( "ERROR #2" ); }
       		}

      		for( int element = 0; element < 9; element++ ) 
        	{    	
	    		String sKey = "Key " + element;
	    		
        		try
        		{
	 		  String data=ri.get(sKey);
	 		  System.out.println(data);
       			}catch( RecordStoreException e ){ System.out.println( "ERROR #2" ); }
       		}
    	
      		
	}
}
