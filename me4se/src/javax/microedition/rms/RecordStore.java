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

package javax.microedition.rms;

import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.kobjects.me4se.impl.*;
import org.kobjects.me4se.*;


/** <font color="#ff0000">Incomplete Experimental Implementation</font>  */ 

public abstract class RecordStore {
    
    static Hashtable recordStores = new Hashtable ();
    

    /**
     * Adds a new record to the record store.
     * @param data The data to be stored in this record. If the record should
     *             have zero-length - no data - this parameter may be set to null
     * @param offset The index of the data buffer of the first databyte for this
     *               record
     * @return The recordid of the new record
     * @exception RecordStoreNotOpenException if the record store is not yet open.
     * @exception InvalidRecordIDException if the recordId is invalid.
     * @exception RecordStoreException if a general recordstoreexception occurs.
     */
    public abstract int addRecord (byte[] data, int offset, int count) 
	throws RecordStoreNotOpenException,
	       RecordStoreException,
	       RecordStoreFullException;
    

    /**
     * Adds the specified RecordListener.
     * 
     * NOT YET IMPLEMENTED
     *
     * @param listener The RecordChangedListener
     */
    public abstract void addRecordListener (RecordListener listener);
    

    /**
     * This method is called when the MIDlet requests to have the record store closed.
     * @exception RecordStoreNotOpenException if the record store is not open
     * @exception RecordStoreException if another record store related Exception occurs
     */
    public abstract void closeRecordStore ()
	throws RecordStoreNotOpenException,
	       RecordStoreException;
    
    /**
     * The record is deleted from the record store.
     *
     * @param recordId The ID of the record to be deleted
     * @exception RecordStoreNotOpenException if the record store is not open
     * @exception RecordStoreException if a different record store-related exception occurs
     */
    public abstract void deleteRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException;
    

    /**
     * Deletes the named record store.
     *
     * NOT YET IMPLEMENTED     
     *
     * @param recordStoreName The record store to be deleted 
     * @exception RecordStoreNotFoundException if the record store could not be found.
     * @exception RecordStoreException if a record store related exception occurs.
     */
    public static void deleteRecordStore (String recordStoreName) 
	throws RecordStoreException,
	       RecordStoreNotFoundException {
    }
    

    //public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated) {
    //return null;
    //}


     /**
     * Returns the last time the record store was modified, in the format used by System.currentTimeMillis().
     *
     * NOT YET IMPLEMENTED
     *
     * @return the last time the record store was modified, in the same format used by System.currentTimeMillis ();
     * @exception RecordStoreNotOpenException if the record store is not open.
     */
    public abstract long getLastModified () throws RecordStoreNotOpenException;


    /**
     * Returns the name of this RecordStore.
     * @return the name of this record store
     * @exception RecordStoreNotOpenException if the record store is not open.
     */
    public abstract String getName () throws RecordStoreNotOpenException;
    

    /**
     * Returns the recordId of the next record to be added to the record store.
     * @return the recordId of the record store to be added to this record store.
     * @exception RecordStoreNotOpenException if the record store is not open.
     * @exception RecordStoreException if another record store related exception occurs
     */
    public abstract int getNextRecordID () throws RecordStoreNotOpenException, RecordStoreException;


    /**
     * Returns the number of records currently in the record store.
     *
     * @return the number of records currently in the opened record store
     * @exception RecordStoreNotOpenException if the record store is not open.
     */
    public abstract int getNumRecords () throws RecordStoreNotOpenException;


    /**
     * Returns a copy of the data stored in the given record.
     *
     * @param recordId The ID of the record to use in this operation
     * @exception RecordStoreNotOpenException if the record store is not open.
     * @exception InvalidRecordIDException if the record store is invalid.
     * @exception RecordStoreException if a general record store exception occurs.
     */
    public abstract byte[] getRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException;
    

    /**
     * Returns the data stored in the given record.
     *
     * NOT YET IMPLEMENTED     
     *
     * @param recordId The ID of the record to be used in this operation.
     * @param buffer The byte array to copy the data.
     * @param offset The index index into the buffer i which to start copiying.
     * @return the number of bytes copied into the buffer, startting at index offset
     * @exception RecordStoreNotOpenException if the record store is not open.
     * @exception InvalidRecordIDException if the record store is invalid.
     * @exception RecordStoreException if a general record store exception occurs.
     * @exception ArrayIndexOutOfBoundsException if the record is larger that the buffer supplied
     */
    public abstract int getRecord (int recordId, byte[] buffer, int offset) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException,
	       ArrayIndexOutOfBoundsException;
    

    /**	
     * Returns the size (in bytes) of the application data available in the given record.
     * @param recordId The ID of the record to be used in this operation.
     * @return the size in bytes of the data available in the record
     * @exception RecordStoreNotOpenException if the record store is not open.
     * @exception InvalidRecordIDException if the record store is invalid.
     * @exception RecordStoreException if a general record store exception occurs.
     */
    public abstract int getRecordSize (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException;
    
    
    /**
     * Returns the amount of space, in bytes, that the record store occupies.
     * @return the size of the record store in bytes 
     * @exception RecordStoreNotOpenException if the record store is not open.
     */
    public abstract int getSize () throws RecordStoreNotOpenException;
    

    /**
     * Returns the amount of additional room (in bytes) available for this record store to grow.
     */
    public abstract int getSizeAvailable () throws RecordStoreNotOpenException;
    

    /**
     * Each time a record store is modified (record added, modified, deleted), it's version is incremented.
     */
    public abstract int getVersion () throws RecordStoreNotOpenException;
    

    /**
     * Returns an arry of names of record stores owned by the application if the stores are private.
     * Note that the function returns NULL if the application does not have any record stores.
     *
     * @return an array of the names of record stores.
     */
    public static String[] listRecordStores () {
        if (MIDletRunner.isApplet) return new String[0];
        File directory = ApplicationManager.manager.getRmsDir ();
        String[] databases = null;
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


        if (directory.isDirectory()) databases = directory.list();
        return databases;
    }
        
    private class FileNameIdx implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return true;
        }
    }
    

    /**
     * Open (and possibly create) a record store. If this record store is already opened, this method
     * returns a reference to the same RecordStore object.
     *
     * @param recordStoreName The unique name, not to exceed 32 characters, of the record store.
     * @param createIfNecesarry If true, the record store will be created if necessary.
     * @return The <code>RecordStore</code> object for the record store.
     * @exception RecordStoreException if a record store-related exception occurs.
     * @exception RecordStoreNotFoundException if the record store could not be found
     *
     */
    public static RecordStore openRecordStore (String recordStoreName, boolean createIfNecessary) 
	throws RecordStoreException,
	       RecordStoreFullException,
	       RecordStoreNotFoundException {

	RecordStoreImpl store = (RecordStoreImpl) recordStores.get 
	    (recordStoreName);

	if (store == null) {
	    store = new org.kobjects.me4se.impl.RecordStoreImpl ();
	    recordStores.put (recordStoreName, store);
	}

	store.open (recordStoreName, createIfNecessary); 

	return store;
    }



    
    /**
     * Removes the specified RecordListener.
     * @param listener the RecordChangedListener
     */
    public abstract void removeRecordListener (RecordListener listener);


    /**
     * Sets the data in the given record to that passed in.
     * @param recordId The ID of the record store.
     * @param newdata The new data buffer to store in the record.
     * @param offset The index into the data buffer of the first new byte to be stored in the record.
     * @param count The number of bytes of the data buffer to use for this record.
     */
    public abstract void setRecord (int recordId, byte[] newData, int offset, int count) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException,
	       RecordStoreFullException;
}









