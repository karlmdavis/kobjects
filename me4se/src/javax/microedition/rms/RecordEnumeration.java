package javax.microedition.rms;

public interface RecordEnumeration {

    public void destroy ();

    public boolean hasNextElement ();

    public boolean hasPreviousElement ();

    public boolean isKeptUpdated ();

    public void keepUpdated (boolean keepUpdated);

    public byte [] nextRecord ()
	throws RecordStoreException, RecordStoreNotOpenException, 
	       InvalidRecordIDException;

    public int nextRecordId () throws InvalidRecordIDException;

    public int numRecords ();

    public byte [] previousRecord ()
	throws RecordStoreException, RecordStoreNotOpenException, 
	       InvalidRecordIDException;

    public int previousRecordId () throws InvalidRecordIDException;

    public void rebuild ();

    public void reset ();

}
