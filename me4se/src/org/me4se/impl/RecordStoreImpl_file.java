package org.me4se.impl;

import javax.microedition.rms.*;
import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.me4se.*;


public class RecordStoreImpl_file extends RecordStoreImpl implements FilenameFilter {

    int version = 0;
    long lastModified = 0L;


    Vector listeners;
    Vector records;
    File file;
    public static File rmsDir = new File ("."); // null for applet

    boolean isApplet () {
	return rmsDir == null;
    }

    public boolean accept (File dir, String name) {
	return name.endsWith (".rms") ? true : false;
    }

    private void changeVersion () {
	version++;
	lastModified = System.currentTimeMillis();
    }

    public void open (String recordStoreName, 
		      boolean create) throws RecordStoreNotFoundException {
	
	if (refCount++ > 0) return;
	
	this.recordStoreName = recordStoreName;

	if (isApplet ()) { 
	    if (!create) {
		refCount = 0;
		throw new RecordStoreNotFoundException ();
	    }

	    records = new Vector ();
	}
	else {
	    file = new File (rmsDir, this.recordStoreName + ".rms");
	    
	    try {
		DataInputStream dis = new DataInputStream 
		    (new FileInputStream (file));
		
		version = dis.readInt ();
		lastModified = dis.readLong();
		int count = dis.readInt ();
		records = new Vector();

		for (int i = 0; i < count; i++) {
		    int length = dis.readInt();
		    if (length >= 0) {
			byte[] buffer = new byte[length];
			dis.readFully(buffer, 0, length);
			records.addElement (buffer);
		    }
		    else {
			records.addElement (null);
		    }
		}
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

	byte[] newData = new byte [numBytes];
	System.arraycopy (data, offset, newData, 0, numBytes);
	records.addElement (newData);

	changeVersion();
	
	if (listeners != null) {
	    for (int i = 0; i < listeners.size(); i++) {
		((RecordListener)listeners.elementAt(i)).recordAdded (this, records.size());
	    }
	}
	
	return records.size ();
    }
    

    public void addRecordListener (RecordListener listener) {
	if (listeners == null)
	    listeners = new Vector ();
	
	listeners.add (listener);
    }
    

    public void closeRecordStore ()
	throws RecordStoreNotOpenException,
	       RecordStoreException {

	if (refCount > 0) refCount--;

	if (isApplet ()) return;

	try {

	    DataOutputStream dos = new DataOutputStream 
		(new FileOutputStream (file)); 
	    
	    dos.writeInt (version);
	    dos.writeLong (lastModified);
	    int cnt = records.size ();
	    dos.writeInt (cnt);

	    for (int i = 0; i < cnt; i++) {
				
		Object obj = records.elementAt(i);
		
		if (obj != null) {
		    byte[] buffer = (byte[])obj;
		    dos.writeInt (buffer.length);
		    dos.write(buffer, 0, buffer.length);
		}
		else {
		    dos.writeInt(-1);		    
		}
	    }

	    dos.flush();
	    dos.close();
	    records = null;
	}  
	catch (IOException ioe) { 
	    throw new RecordStoreException 
		("ERROR closing the recordstore: " + ioe.toString ());
	}
    }
    

    public void deleteRecordStoreImpl () throws RecordStoreException {
	if (refCount != 1) 
	    throw new RecordStoreException ("RecordStore is open!");

	if (!isApplet ()) file.delete ();
    }


    public void deleteRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException {
	

	checkId (recordId);
	records.setElementAt (null, recordId-1);
	
	changeVersion();

	if (listeners != null) {
	    for (int i = 0; i < listeners.size(); i++) {
		((RecordListener)listeners.elementAt(i)).recordDeleted (this, recordId);
	    }
	}
    }
    

    public long getLastModified () throws RecordStoreNotOpenException {
	checkOpen();
	return lastModified;
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
	checkOpen();
	
	int size = 0;
	
	// version
	size += 4;
	// last modified
	size += 8;
	
	for (int i = 0; i < records.size(); i++) {
	    Object obj = records.elementAt(i);
	    // size of buffer
	    size += 4;
	    // the buffer
	    if (obj != null) 
		size += ((byte[])obj).length; 
	    
	}

	
	return size;
    }
    

    public int getSizeAvailable () throws RecordStoreNotOpenException {
	return Integer.MAX_VALUE;
    }
    
    
    public int getVersion () throws RecordStoreNotOpenException {
	checkOpen();
	return version;
    }
    
    
    public String [] listRecordStoresImpl () {
    
	String[] databases;
    
	if (isApplet ()) {
	    databases = new String [recordStores.size ()];
	    int i = 0;
	    for (Enumeration e = recordStores.keys (); e.hasMoreElements (); ) 
		databases [i++] = (String) e.nextElement ();
	}
	else {
	    File directory = rmsDir;
	    // SV: We could add a file-extension to every saved RMS and filter then 
	    // (look at private class below).
	    // Otherwise this solution is not very intelligent because it lists every File in
	    // the current Dirctory as RMS.
	    

	    // SH: I have created a special dir for RMS, so a special extension should not
	    // be necessary. However, perhaps it would make sense to shift some of the 
	    // static functions to non-static functions in a "meta" RMS, in order to allow
	    // different "org.kobjects.me4se.impl.RecordStoreImpl_" classes for record
	    // stores in files and accessing servlets (mechanism similar to 
	    // Connection_http etc: RecordStoreImpl_file, RecordStoreImpl_http). 
	    //
	    // A command line/applet parameter specifying the RMS location/impl. would 
	    // probably also be a good idea....
	    

	    databases =  directory.isDirectory()
		? directory.list(this) : new String [0];
	}

	for (int i = 0; i < databases.length; i++) {
	    databases[i] = databases[i].substring (0, databases[i].length() - 4);
	}
	
	return databases;
    }
    
    public void removeRecordListener (RecordListener listener) {
	if (listeners != null)
	    listeners.remove (listener);
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
	
	changeVersion();

	if (listeners != null) {
	    for (int i = 0; i < listeners.size(); i++) {
		((RecordListener)listeners.elementAt(i)).recordChanged (this, recordId);
	    }
	}
    }
}




