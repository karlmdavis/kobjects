/*
 * $Id: RecordStoreImpl.java,v 1.4 2001/11/05 19:51:35 mkroll Exp $
 */

package org.me4se.impl;

import javax.microedition.rms.*;
import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.me4se.*;


public abstract class RecordStoreImpl extends RecordStore {

    public static Hashtable recordStores = new Hashtable ();
    public static RecordStoreImpl metaStore = newInstance ();
    
    protected Vector listeners;
    
    String recordStoreName;
    int refCount;


    public void addRecordListener (RecordListener listener) {
	if (listeners == null)
	    listeners = new Vector ();
	
	listeners.add (listener);
    }

    
    public void removeRecordListener (RecordListener listener) {
	if (listeners != null)
	    listeners.remove (listener);
    }
    
       
    public abstract void deleteRecordStoreImpl () throws RecordStoreException ;


    void checkOpen () throws RecordStoreNotOpenException {
	if (refCount == 0) 
	    throw new RecordStoreNotOpenException 
		("RecordStore not open: "+recordStoreName);
    } 


    void checkId (int index) throws RecordStoreException {
	checkOpen ();
	if (index < 1 || index >= getNextRecordID ()) 
	    throw new InvalidRecordIDException 
		("Id "+index+" not valid in recordstore "+recordStoreName);
    }



    public static RecordStoreImpl newInstance () {
	return new org.me4se.impl.RecordStoreImpl_file ();
    }

    
    public abstract String [] listRecordStoresImpl ();

    public abstract void open (String recordStoreName, 
			       boolean create) throws RecordStoreNotFoundException;
}


/*
 * $Log: RecordStoreImpl.java,v $
 * Revision 1.4  2001/11/05 19:51:35  mkroll
 * Moved addListener() and removeListener() to RecordStoreImpl.
 *
 */