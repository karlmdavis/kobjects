package org.kobjects.db;

/**
 * Überschrift:
 * Beschreibung:
 * Copyright:     Copyright (c) 2001
 * Organisation:
 * @author
 * @version 1.0
 */

import java.io.*;
import java.util.*;


public abstract class Table {

    protected Vector fields = new Vector ();


    
    public void addField (String name, int type) {
        addField(new Field (type, name, name, 0, 
                            //TextField.ANY, 
                            null, null));
    }
    
    
    /** Add real functionality here */
    
    public void addField (Field field) {
        fields.addElement (field);
    }

    
    public int getFieldCount() {
        return fields.size ();
    }

    /*
    public abstract int getRecordCount ();
    */  

    /** creates a new record. */

    public Record createRecord () {
        return new Record (this, -1);
    }


    /** Returns a record positioned before the first record. next ()
        must be called before the first record is accessible */

    public Record select () {
        return new Record (this, 0);
    } 


    /** returns the record with the given id, where 1 <= id <=
        getRecordCount (). In contrast to select (), the record is
        valid without calling next() before. next will jump to the next
        record.  */

    public Record select (int id) {
        Record r = new Record (this, id);
        refresh (r);
        return r;
    }

        
    /** refreshes the given record from the database. Throws an
        IllegalArgumentException if the given record id is invalid. */

    public abstract void refresh (Record record);

    /** updates the database from the given record. If the record is
        newly created, the ID is updated to the real position of the
        record.  */

    public abstract void update (Record record); 


    public Field getField(int i) {
        return (Field) fields.elementAt (i);
    }
    
    
    public int findField(String name) {
        for (int i = 0; i < fields.size (); i++) {
            if (getField(i).name.equals(name)) return i;
        }
        
        return -1;
    }
    
    
    public abstract void close();
}





