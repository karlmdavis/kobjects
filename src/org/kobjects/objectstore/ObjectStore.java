package org.kobjects.objectstore;


import java.util.*;
import java.io.*; 
import javax.microedition.rms.*;

import org.kobjects.serialization.*;

public class ObjectStore {
    
    static final byte NULL = (byte) 0;
    static final byte IDXREF = (byte) 1;
    static final byte INT = (byte) 2;
    static final byte STRING = (byte) 3;
    static final byte IDREF = (byte) 4;
    static final byte VECTOR = (byte) 5;

    protected RecordStore store;

    
    public ObjectStore (String rmsName) throws RecordStoreException {
	
	store = RecordStore.openRecordStore (rmsName, true);
	
    }
    
    
    protected String [] getName (Object obj) {
	return new String [] {obj.getClass ().getName ()};
    }
    
    
    protected Object newInstance (String name, String namespace, String id) {
	try {
	    return Class.forName (name).newInstance ();
	}
	catch (Exception e) {
	    throw new RuntimeException ("cannot instantiate "+name+":"+e);
	}
    }
    
    /** returns the underlying record store. use with care!  */

    public RecordStore getRecordStore () {
	return store;
    }
    
    public void write (Object obj) throws RecordStoreException {
	
	// clear all records?
	
	Vector multiRef = new Vector ();
	multiRef.addElement (obj);
	int idx = 0;
	while (idx < multiRef.size ()) {
	    write (idx + 1, multiRef.elementAt (idx), multiRef);
	}
    }
    
    

    /** write the record at the given index position with the given
	object */
    
    public void write (int index, Object obj, 
		       Vector multiRef) throws RecordStoreException {
	
	try {
	    
	    ByteArrayOutputStream bos = new ByteArrayOutputStream ();
	    DataOutputStream dos = new DataOutputStream (bos);
	    
	    String [] name = getName (obj);
	    dos.writeByte ((byte) name.length);
	    for (int i = 0; i < name.length; i++)
		dos.writeUTF (name [i]);
	
	    if (obj instanceof KvmSerializable) {
		KvmSerializable sobj = (KvmSerializable) obj;
		int cnt = sobj.getPropertyCount ();
		PropertyInfo info = new PropertyInfo ();
		for (int i = 0; i < cnt; i++) {
		    info.nonpermanent = false;
		    sobj.getPropertyInfo (i, info);
		    if (!info.nonPermanent)
			writeProperty (dos, sobj.getProperty (i), multiRef);
		}
	    }
	    else if (obj instanceof Vector) {
		Vector v = (Vector) obj;
		int cnt = v.size ();
		dos.writeInt (cnt);
		for (int i = 0; i < cnt; i++)
		    writeProperty (dos, v.elementAt (i), multiRef);
	    }
	    else
		throw new RuntimeException 
		    ("Unsupported type: "+obj+ " / "+obj.getClass ());
	    
	    byte [] data = bos.toByteArray ();
	    
	    if (index == store.getNextRecordID ()) 
		store.addRecord (data, 0, data.length);
	    else
		store.setRecord (index, data, 0, data.length);
	    
	    //	    index.put (obj, new Integer (idx));
	}
	catch (IOException e) {
	    throw new RuntimeException ("impossible error: "+e);
	}
    }
    
    
    public void writeProperty (DataOutputStream dos, 
			       Object obj, Vector multiRef) throws RecordStoreException, IOException {
	
	if (obj == null) {
	    dos.writeByte (ObjectStore.NULL);
	}
	else if (obj instanceof Integer) {
	    dos.writeByte (ObjectStore.INT);
	    dos.writeInt (((Integer) obj).intValue ());
	}
	else if (obj instanceof String) {
	    dos.writeByte (ObjectStore.STRING);
	    dos.writeUTF ((String) obj);
        }
	else if (obj instanceof Vector) {
	    dos.writeByte (ObjectStore.VECTOR);
	    Vector v = (Vector) obj;
	    int len = v.size ();
	    dos.writeInt (len);
	    for (int i = 0; i < len; i++) 
		writeProperty (dos, v.elementAt (i), multiRef);
	}
	else { 
	    String [] name = getName (obj);
	    
	    if (name.length == 3) {
		dos.writeByte (ObjectStore.IDREF);
		dos.writeUTF (name [2]);
	    }
	    else {
		int idx = multiRef.indexOf (obj);
		if (idx == -1) {
		    idx = multiRef.size ();
		    multiRef.addElement (obj);
		}
		
		dos.writeByte (ObjectStore.IDXREF);
		dos.writeInt (idx+1); // record id = vector index+1
		
		// if an id is available, write the idref,
		// otherwise generate an id and write the idref
	    }
	}
    }

    

    /** if the object is null, a new one will be created,
	otherwise, the given object is filled */
    
    public Object read (int index, 
			Object obj) throws RecordStoreException {
	
	try {
	    DataInputStream dis = new DataInputStream 
		(new ByteArrayInputStream (store.getRecord (index)));
	    
	    String [] name =  new String [3];
	    int ncnt = dis.readByte ();
	    for (int i = 0; i < ncnt; i++) 
		name [i] = dis.readUTF ();

	    if (obj == null) 
		obj = newInstance (name[0], name[1], name [2]);
	    
	    
	    if (obj instanceof KvmSerializable) {
		KvmSerializable kobj = (KvmSerializable) obj; 
		int cnt = kobj.getPropertyCount ();
		PropertyInfo info = new PropertyInfo ();
		for (int i = 0; i < cnt; i++) {
		    info.nonpermanent = false;
		    kobj.getPropertyInfo (i);
		    if (!info.nonpermanent) 
			kobj.setProperty (i, readProperty (dis));
		}
	    }
	    else if (obj instanceof Vector) {
		int cnt = dis.readInt ();
		Vector v = (Vector) obj;
		v.setSize (cnt);
		for (int i = 0; i < cnt; i++) 
		    v.setElementAt (readProperty (dis), i);
	    }
	    else
		throw new RuntimeException ("Illegal type!");
	    
	    return obj;
	}
	catch (IOException e) {
	    throw new RuntimeException ("Impossible: "+e);
	}
    }	
    
    Object readProperty (DataInputStream dis) throws IOException, RecordStoreException {
	switch (dis.readByte ()) {
	    
	case STRING: return dis.readUTF ();
	case NULL: return null;
	case IDXREF: return read (dis.readInt (), null);
	case IDREF: {
	    String id = dis.readUTF ();
	    return newInstance (null, null, id);
	}
	case VECTOR:
	    {
		Vector v = new Vector ();
		int len = dis.readInt ();
		for (int i = 0; i< len; i++)
		    v.addElement (readProperty (dis));
		return v;
	    }
	default:
	    throw new RuntimeException ("unknown type code: "+dis);
	}
    }


    public void close () throws RecordStoreException {
	store.closeRecordStore ();
    }
}
