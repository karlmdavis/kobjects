package org.me4se.impl;

import javax.microedition.rms.*;


public class RecordEnumerationImpl implements RecordEnumeration {

    RecordStore store;
    RecordFilter filter;
    RecordComparator comparator; 
    boolean keepUpdated;

    public RecordEnumerationImpl (RecordStore store, 
				  RecordFilter filter, 
				  RecordComparator comparator, 
				  boolean keepUpdated) {

	this.store = store;
	this.filter = filter;
	this.comparator = comparator;
	this.keepUpdated = keepUpdated;
    }

}
