package org.me4se.impl;

import javax.microedition.rms.*;
import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.me4se.*;


public abstract class RecordStoreImpl extends RecordStore {

    public static Hashtable recordStores = new Hashtable ();
    public static RecordStoreImpl metaStore = newInstance ();


    String recordStoreName;
    int refCount;


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
