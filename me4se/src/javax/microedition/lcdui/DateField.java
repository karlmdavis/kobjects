package javax.microedition.lcdui;

// currently, just a dummy placeholder!

import java.util.*;

public class DateField extends Item {

    public static final int DATE = 1;
    public static final int TIME = 2;
    public static final int DATE_TIME = 3;
    
    int type;
    TimeZone timeZone;
    Date date = new Date ();
    java.awt.Button button = new java.awt.Button ("set");
    
    public DateField (String label, int type, TimeZone timezone) {
	super (label);
	this.type = type;
	this.timeZone = timeZone;
    }
	

    public DateField (String label, int type) {
	this (label, type, TimeZone.getDefault());
    }

    public java.awt.Component getField () {
	return button;
    }
}
