package org.kobjects.rms;


import javax.microedition.rms.*;
import java.io.*;

public class RmsIndex {

    RecordStore store;
    Node root;
    int n = 4;
    
    class Node {
	int index;
	int size;
	
	String [] keys = new String [n+n+1];
	String [] values = new String [n+n+1];
	int [] children = new int [n+n + 2];

	/** Create a new Node */

	Node () throws RecordStoreException {
	    index = store.addRecord (null, 0, 0);
	}

	Node (Node split) throws RecordStoreException {
	    this ();
	    System.arraycopy (split.keys, n+1, keys, 0, n);
	    System.arraycopy (split.values, n+1, values, 0, n);
	    System.arraycopy (split.children, n+1, children, 0, n+1);
	    size = n;
	    split.size = n;	    
	    split.store ();
	    store ();
	}


	/** Load the node at the given index position */
	
	Node (int index) throws RecordStoreException {
	    this.index = index;
	    byte [] data = store.getRecord (index);
	    
	    if (data == null || data.length == 0) return;

	    try {
		DataInputStream dis = new DataInputStream 
		    (new ByteArrayInputStream (data));
		
		size = dis.readInt ();
		
		for (int i = 0; i < size; i++) {
		    children [i] = dis.readInt ();
		    keys [i] = dis.readUTF ();
		    values [i] = dis.readUTF ();
		}
	    
		children [size] = dis.readInt (); 
	    }
	    catch (IOException e) {
		throw new RuntimeException (e.toString ());
	    }
	} 



	public void put (String key, 
			 String value) throws RecordStoreException {

	    int i;
	    for (i = 0; i < size; i++) { 
		int cmp = key.compareTo (keys [i]);
		if (cmp == 0) {
		    values [i] = value;
		    store ();
		    return;
		}
		else if (cmp < 0) break;
	    }

	    int newIndex = 0;

	    if (children [i] != 0) {
		Node child = new Node (children[i]);
		child.put (key, value);

		if (child.size < n+n+1) return;

		Node split = new Node (child);
		newIndex = split.index; 
		
		key = child.keys [n];
		value = child.values [n];
	    }

	    System.arraycopy (keys, i, keys, i+1, size-i);
	    System.arraycopy (values, i, values, i+1, size-i);
	    System.arraycopy (children, i+1, children, i+2, size-i);
	    
	    keys [i] = key;
	    values [i] = value;
	    children [i+1] = newIndex;
	    size ++;

	    if (size < n+n+1)
		store ();
	}


	public void store () throws RecordStoreException {

	    try {
		ByteArrayOutputStream bos = new ByteArrayOutputStream ();
		DataOutputStream dos = new DataOutputStream (bos);
		dos.writeInt (size);
		for (int i = 0; i < size; i++) {
		    dos.writeInt (children [i]);
		    dos.writeUTF (keys [i]);
		    dos.writeUTF (values [i]);
		}
		dos.writeInt (children [size]);
		byte [] data = bos.toByteArray ();
		store.setRecord (index, data, 0, data.length);
	    }
	    catch (IOException e) {
		throw new RuntimeException (e.toString ());
	    }
	}
    }
    


    public RmsIndex (String name) throws RecordStoreException {

	store = RecordStore.openRecordStore (name, true);
	root = store.getNumRecords () == 0 ? new Node () : new Node (1);
    }



    public String get (String key) throws RecordStoreException {

	Node current = root;
	
	while (true) {
	    int i;
	    for (i = 0; i < current.size; i++) { 
		int cmp = key.compareTo (current.keys [i]);
		if (cmp == 0) return current.values [i];
		else if (cmp < 0) break;
	    }
	    if (current.children [i] == 0) return null;
	    current = new Node (current.children [i]);
	}
    }


    /** return value is where to split the child */

    public void put (String key, String value) throws RecordStoreException {

	root.put (key, value);
	if (root.size < n+n+1) return;

	// split root....

	Node left = root;
	Node root = new Node ();

	left.index = root.index;
	root.index = 1;

	Node right = new Node (left);

	root.keys [0] = left.keys [n];
	root.values [0] = left.values [n];
	root.children [0] = left.index;
	root.children [1] = right.index;
	root.size = 1;
	root.store ();
    }
}


