// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: Dummy class with complete API
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

package javax.microedition.lcdui;



import java.util.*;

public class DateField extends Item {

    public static final int DATE = 1;
    public static final int TIME = 2;
    public static final int DATE_TIME = 3;
    
    int mode;
    TimeZone timeZone;
    Date date = new Date ();
    java.awt.Button button = new java.awt.Button ("set");
    
    public DateField (String label, int mode, TimeZone timezone) {
	super (label);
	this.mode = mode;
	this.timeZone = timeZone;
    }
	

    public DateField (String label, int mode) {
	this (label, mode, TimeZone.getDefault());
    }

    java.awt.Component getField () {
	return button;
    }


    public Date getDate () {
	return date;
    }
    
    public int getInputMode () {
	return mode;
    }


    public void setInputMode (int mode) {
	this.mode = mode;
    }


    public void setDate (Date date) {
	this.date = date;
    }
}
