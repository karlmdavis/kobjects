package javax.microedition.rms;

import javax.microedition.midlet.ApplicationManager;
import java.util.*;
import java.io.*;
import org.me4se.impl.*;
import org.me4se.*;


public abstract class RecordStore {
    

    /** Adds a new record to the record store.

        @param data The data to be stored in this record. If the
                    record should have zero-length - no data - this
                    parameter may be set to null
	@param offset The index of the data buffer of the first
	              databyte for this record
	@return The recordid of the new record
	@exception RecordStoreNotOpenException if the record store is
	           not yet open.
	@exception InvalidRecordIDException if the recordId is invalid.
	@exception RecordStoreException if a general
	           recordstoreexception occurs.  */

    public abstract int addRecord (byte[] data, int offset, int count) 
	throws RecordStoreNotOpenException,
	       RecordStoreException,
	       RecordStoreFullException;
    

    /**
     * Adds the specified RecordListener.
     *
     * @param listener The RecordChangedListener
     */
    public abstract void addRecordListener (RecordListener listener);
    

    /** This method is called when the MIDlet requests to have the
       record store closed.

       @exception RecordStoreNotOpenException if the record store is
                  not open
       @exception RecordStoreException if another record store related
                  Exception occurs */

    public abstract void closeRecordStore ()
	throws RecordStoreNotOpenException,
	       RecordStoreException;

    
    /** The record is deleted from the record store.
	@param recordId The ID of the record to be deleted
	@exception RecordStoreNotOpenException if the record store is not open
	@exception RecordStoreException if a different record
	           store-related exception occurs */

    public abstract void deleteRecord (int recordId) 
	throws RecordStoreNotOpenException,
	       InvalidRecordIDException,
	       RecordStoreException;
    

    /**
     * Deletes the named record store.
     *
     * @param recordStoreName The record store to be deleted 
     * @exception RecordStoreNotFoundException if the record store could not be found.
     * @exception RecordStoreException if a record store related exception occurs.
     */
    public static void deleteRecordStore (String recordStoreName) 
	throws RecordStoreException,
	       RecordStoreNotFoundException {

	((RecordStoreImpl) openRecordStore 
	 (recordStoreName, false)).deleteRecordStoreImpl ();

	RecordStoreImpl.recordStores.remove (recordStoreName);
    }
    

    public RecordEnumeration enumerateRecords (RecordFilter filter, 
					       RecordComparator comparator, 
					       boolean keepUpdated) 

	throws RecordStoreNotOpenException {

	return new RecordEnumerationImpl 
	    ((RecordStoreImpl) this, filter, comparator, keepUpdated);
    }


     /** Returns the last time the record store was modified, in the
         format used by System.currentTimeMillis().
     
     @return the last time the record store was modified, in the same
             format used by System.currentTimeMillis ();
     @exception RecordStoreNotOpenException if the record store is not
                open. */

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
    

    /** Returns the data stored in the given record.
     
      @param recordId The ID of the record to be used in this
                      operation.
      @param buffer The byte array to copy the data.
      @param offset The index index into the buffer i which to start copiying.
      @return the number of bytes copied into the buffer, startting at
              index offset
      @exception RecordStoreNotOpenException if the record store is
                 not open.
      @exception InvalidRecordIDException if the record store is
                 invalid.
      @exception RecordStoreException if a general record store
                 exception occurs.
      @exception ArrayIndexOutOfBoundsException if the record is larger 
                 that the buffer supplied */

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
    public static String[] listRecordStores ()  {

	return RecordStoreImpl.metaStore.listRecordStoresImpl ();

    }
    /*       private class FileNameIdx implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return true;
        }
    }
    */

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

	RecordStoreImpl store = (RecordStoreImpl) 
	    RecordStoreImpl.recordStores.get 
	    (recordStoreName);

	if (store == null) {
	    store = RecordStoreImpl.newInstance ();
	    RecordStoreImpl.recordStores.put (recordStoreName, store);
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









