// kAWT - Kilobyte Abstract Window Toolkit
//
// Copyright (C) 1999-2000 by Michael Kroll & Stefan Haustein GbR, Essen
//
// Contact: kawt@kawt.de
// General Information about kAWT is available at: http://www.kawt.de
//
// Using kAWT for private and educational and in GPLed open source
// projects is free. For other purposes, a commercial license must be
// obtained. There is absolutely no warranty for non-commercial use.
//
//
// 1. BECAUSE THE PROGRAM IS LICENSED FREE OF CHARGE, THERE IS NO
//    WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY APPLICABLE
//    LAW.  EXCEPT WHEN OTHERWISE STATED IN WRITING THE COPYRIGHT
//    HOLDERS AND/OR OTHER PARTIES PROVIDE THE PROGRAM "AS IS" WITHOUT
//    WARRANTY OF ANY KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING, BUT
//    NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
//    FITNESS FOR A PARTICULAR PURPOSE.  THE ENTIRE RISK AS TO THE
//    QUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU.  SHOULD THE
//    PROGRAM PROVE DEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY
//    SERVICING, REPAIR OR CORRECTION.
//   
// 2. IN NO EVENT UNLESS REQUIRED BY APPLICABLE LAW OR AGREED TO IN
//    WRITING WILL ANY COPYRIGHT HOLDER, OR ANY OTHER PARTY WHO MAY
//    MODIFY AND/OR REDISTRIBUTE THE PROGRAM AS PERMITTED ABOVE, BE
//    LIABLE TO YOU FOR DAMAGES, INCLUDING ANY GENERAL, SPECIAL,
//    INCIDENTAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR
//    INABILITY TO USE THE PROGRAM (INCLUDING BUT NOT LIMITED TO LOSS OF
//    DATA OR DATA BEING RENDERED INACCURATE OR LOSSES SUSTAINED BY YOU
//    OR THIRD PARTIES OR A FAILURE OF THE PROGRAM TO OPERATE WITH ANY
//    OTHER PROGRAMS), EVEN IF SUCH HOLDER OR OTHER PARTY HAS BEEN
//    ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
//   
//    END OF TERMS AND CONDITIONS
// 

package org.kobjects.me4se.impl;

import javax.microedition.rms.*;
import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.kobjects.me4se.*;


public class RecordStoreImpl extends RecordStore {

    private Vector records;
    private String recordStoreName;
    private int refCount;

    File file;
    
    private void checkOpen () throws RecordStoreNotOpenException {
	if (refCount == 0) 
	    throw new RecordStoreNotOpenException 
		("RecordStore not open: "+recordStoreName);
    } 
    

    private void checkId (int index) throws RecordStoreException {
	checkOpen ();
	if (index < 1 || index > records.size ()) 
	    throw new InvalidRecordIDException 
		("Id "+index+" not valid in recordstore "+recordStoreName);
    }
    
    
    public RecordStoreImpl () {
    }

    public void open (String recordStoreName, 
		      boolean create) throws RecordStoreNotFoundException {
	
	if (refCount++ > 0) return;
	
	this.recordStoreName = recordStoreName;

	if (MIDletRunner.isApplet) { 
	    if (!create) {
		refCount = 0;
		throw new RecordStoreNotFoundException ();
	    }

	    records = new Vector ();
	}
	else {
	    file = new File (ApplicationManager.manager.getRmsDir (), recordStoreName);
	    
	    try {

		ObjectInputStream ois = new ObjectInputStream 
		    (new FileInputStream (file));
		
		records = (Vector) ois.readObject ();

		//System.out.println ("read: "+records);
		ois.close ();
	    } 
	    catch (Exception ioe) {
		
		if (!create) {
		    refCount = 0;
		    throw new RecordStoreNotFoundException ();
		}

		records = new Vector ();		
	    }
	}
    }


    public int addRecord (byte[] data, int offset, int numBytes) 
	throws RecordStoreNotOpenException,
	       RecordStoreException,
	       RecordStoreFullException {

	checkOpen ();

	byte[] newData = new byte[numBytes];
	System.arraycopy (data, offset, newData, 0, numBytes);
	records.addElement (newData);
	return records.size ();
    }
    

    public void addRecordListener (RecordListener listener) {
	throw new RuntimeException ("Not yet supported!");
    }
    

    public void closeRecordStore ()
	throws RecordStoreNotOpenException,
	       RecordStoreException {

	//System.out.println ("closeRecordStore "+recordStoreName + " / "+refCount);

	if (refCount > 0) refCount--;

	if (MIDletRunner.isApplet) return;

	try {
	    ObjectOutputStream p = new ObjectOutputStream 
		(new FileOutputStream (file)); 

	    //System.out.println ("writing: "+records);
	    p.writeObject(records); 
	    p.close ();
	    records = null; 
	}  
	catch (IOException ioe) { 
	    throw new RecordStoreException 
		("ERROR closing the recordstore: " + ioe.toString ());
	}
    }
    

    public void deleteRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException {
	

	checkId (recordId);
	records.setElementAt (null, recordId-1);
    }
    


    //public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) {
    //return null;
    //}


    public long getLastModified () throws RecordStoreNotOpenException {
	throw new RuntimeException ("Not yet supported!");
    }


    public String getName () throws RecordStoreNotOpenException {
	checkOpen ();
	return recordStoreName;
    }
    

    public int getNextRecordID () 
	throws RecordStoreNotOpenException, RecordStoreException {
	checkOpen ();
	return records.size ()+1;
    }


    public int getNumRecords () throws RecordStoreNotOpenException {
	checkOpen ();
	return records.size ();
    }


    public byte[] getRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException {

	checkId (recordId);
	return (byte []) records.elementAt (recordId - 1);
    }
    

    public int getRecord (int recordId, byte[] buffer, int offset) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException,
	       ArrayIndexOutOfBoundsException {

	byte [] data = getRecord (recordId);
	System.arraycopy (data, 0, buffer, offset, data.length);
	return data.length;
    }
    

    public int getRecordSize (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException {
	

	return getRecord (recordId).length;
    }
    

    public int getSize () throws RecordStoreNotOpenException {
	throw new RuntimeException ("not yet supported");
    }
    

    public int getSizeAvailable () throws RecordStoreNotOpenException {
	throw new RuntimeException ("not yet supported");
    }
    

    public int getVersion () throws RecordStoreNotOpenException {
	throw new RuntimeException ("not yet supported");
    }
    

    public void removeRecordListener (RecordListener listener) {
	throw new RuntimeException ("not yet supported");
    }


    public void setRecord (int recordId, byte[] data, int offset, int numBytes) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException,
	       RecordStoreFullException {

	checkId (recordId);

	byte[] newData = new byte[numBytes];
	System.arraycopy (data, offset, newData, 0, numBytes);
	records.setElementAt (newData, recordId-1);
	
    }
}
