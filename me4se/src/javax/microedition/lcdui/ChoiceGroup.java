// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete, optional image functionality missing
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


import java.util.Vector;


public class ChoiceGroup extends Item implements Choice {


    java.awt.Panel panel = new java.awt.Panel (new java.awt.GridLayout (1,0));
    int type;
    java.awt.CheckboxGroup group;
    Vector images = new Vector ();

    public ChoiceGroup (String label, int type) {
	super (label);
	this.type = type;
	if (type == Choice.EXCLUSIVE) 
	    group = new java.awt.CheckboxGroup ();
    }

    
    public ChoiceGroup (String label, int type, 
			String [] stringItems, Image [] imageItems) {
	this (label, type);
	for (int i = 0; i < stringItems.length; i++)  
	    append (stringItems [i], null); //imageItems [i]);
    }

    /** Internal method */
						 
    java.awt.Component getField () { 
	return panel;
    }
				    
    
    java.awt.Checkbox getCheckbox (int i) {
	return (java.awt.Checkbox) panel.getComponent (i);
    }
    
    
    public int append (String stringItem, Image imageItem) {
	insert (images.size ()-1, stringItem, imageItem);
        return images.size () - 1;
    }
    

    public void delete (int index) {
	panel.remove (index);
	images.remove (index);
    }
    
    
    public Image getImage (int index) {
	return (Image) images.elementAt (index);
    }
    

    public int getSelectedFlags (boolean[] flags) { 
	for (int i = 0; i < images.size (); i++) 
	    flags [i] = getCheckbox (i).getState ();

	for (int i = images.size (); i < flags.length; i++) 
	    flags [i] = false;
	
	return images.size ();
    }
    
    
    public int getSelectedIndex () {
	if (type == MULTIPLE) return -1;
	
	for (int i = 0; i < panel.getComponentCount (); i++) 
	    if (getCheckbox (i).getState ()) 
		return i;
	
	return -1;
    }
    
    
    public String getString (int index) {
	return getCheckbox (index).getLabel ();
    }
    

    public void insert (int index, String stringItem, Image imageItem) {
	panel.add (new java.awt.Checkbox (stringItem, group, false), index);
        if (!(imageItem == null)) {
            images.insertElementAt (imageItem, index);
        }
    }
    

    public boolean isSelected (int index) {
	return getCheckbox (index).getState ();
    }


    public void set (int index, String str, Image img) {
	delete (index);
	insert (index, str, img);
    }


    public void setLabel (int index, String s) {
	getCheckbox (index).setLabel (s);
    }

    public void setSelectedFlags (boolean [] flags) {
	
	if (type == MULTIPLE) {
	    for (int i = 0; i < images.size (); i++) {
		if (flags [i]) {
		    getCheckbox (i).setState (true);
		    return;
		}
	    }
	    if (size () > 0) 
		setSelectedIndex (0, true);
	}
	else 
	    for (int i = 0; i < images.size (); i++) 
		getCheckbox (i).setState (flags [i]);
    }
    

    public void setSelectedIndex (int index, boolean state) {
	getCheckbox (index).setState (state);
    }

    
    public int size () {
	return images.size ();
    }

}
