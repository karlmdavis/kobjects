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




public class Record {
    
    private Table table;
    private int id;
    private Object[] values;
    private boolean modified;
    
    
   

    protected Record (Table table, int id) {
        this.table = table;
        this.id = id;
        values = new Object[table.getFieldCount()];
    }
    
    
    public void clear() {
        values = new Object[table.getFieldCount()];
        modified = true;
    }
    
    
    public Object getObject (int column) {
        return values[column];
    }
    

    public void setObject (int column, Object value) {
        values[column] = value;
        modified = true;
    }

    
    public boolean getBoolean(int column) {
        Boolean b = (Boolean)getObject(column);
        return (b == null) ? false : b.booleanValue();
    }
    

    public int getInteger(int column) {
        Integer i = (Integer)getObject(column);
        return (i == null) ? 0 : i.intValue();
    }
    
    
    public String getString(int column) {
        Object o = getObject(column);
        return (o == null) ? null : o.toString();
    }
    

    public byte[] getBinary(int column) {
        return (byte[])getObject(column);
    }
    

    public void setBoolean (int column, boolean value) {
        setObject(column, new Boolean(value));
    }
    

    public int getId () {
        return id;
    }

    public void setInteger (int column, int value) {
        setObject(column, new Integer(value));
    }
    

    public void setString (int column, String value) {
        setObject(column, value);
    }

    
    public void setBinary(int column, byte[] value) {
		//byte[] bytes = new byte[value.length];
        //System.arraycopy(value, 0, bytes, 0, value.length);
        setObject(column, value); // was: bytes
    }
    

    public boolean isModified() {
        return modified;
    }


   
    public Table getTable() {
        return table;
    }


    public boolean next () {
        int save = id;
        try {
            id++;
            table.refresh (this);
            return true;
        }
        catch (IllegalArgumentException e) {
            id = save;
            return false;
        }
    }
}






