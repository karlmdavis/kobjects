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
import java.awt.event.*;

public class List extends Screen implements Choice {

    public static final Command SELECT_COMMAND 
	= new Command ("", Command.SCREEN, 0);

    int type;
    java.awt.List list = new java.awt.List ();

    //Vector strings = new Vector ();
    Vector images = new Vector ();

    public List (String title, int listType) {
	super (title);
	type = listType;
	panel.add ("Center", list);

	list.addItemListener (new ItemListener () {
		public void itemStateChanged (ItemEvent ev) {
		    if (type == IMPLICIT
			&& ev.getStateChange () == ItemEvent.SELECTED) 
			sendCommand (SELECT_COMMAND);
		}
	    });
    }
    
    public List (String title, int listType, String[] stringElements, Image[] imageElements) {
        this(title, listType);
        if (imageElements != null) {
            for (int i=0; i<stringElements.length; i++) 
                append(stringElements[i],imageElements[i]);
        }
        else {
            for (int i=0; i<stringElements.length; i++) 
                append(stringElements[i],Image.createImage(1,1));
        }
    }

    public int append (String s, Image i) {
	list.add (s);
	images.addElement (i);
	return list.getItemCount ()-1;
    }


    public void delete (int index) {
	panel.remove (index);
        if (!images.isEmpty()) 
	    images.remove (index);
    }
    
    
    public Image getImage (int index) {
	return (Image) images.elementAt (index);
    }
    

    public int getSelectedFlags (boolean[] flags) { 
	for (int i = 0; i < images.size (); i++) 
	    flags [i] = list.isIndexSelected (i);

	for (int i = images.size (); i < flags.length; i++) 
	    flags [i] = false;
	
	return images.size ();
    }
    
    public int getSelectedIndex () {
	if (type == MULTIPLE) return -1;
	return list.getSelectedIndex ();
    }

    
    public String getString (int index) {
	return list.getItem (index);
    }
    

    public void insert (int index, String stringItem, Image imageItem) {
	list.add (stringItem, index);
	images.insertElementAt (imageItem, index);
    }
    

    public boolean isSelected (int index) {
	return list.isIndexSelected (index);
    }


    public void set (int index, String str, Image img) {
	list.replaceItem (str, index);
	images.setElementAt (img, index);
    }


 
    public void setSelectedFlags (boolean [] flags) {
	
	if (type != MULTIPLE) {
	    for (int i = 0; i < images.size (); i++) {
		if (flags [i]) {
		    list.select (i);
		    return;
		}
	    }
	    if (size () > 0) 
		list.select (0);
	}
	else 
	    for (int i = 0; i < images.size (); i++) {
		if (flags [i]) list.select (i);
		else list.deselect (i);
	    }
    }
    

    public void setSelectedIndex (int i, boolean state) {
	if (state) list.select (i);
	else list.deselect (i);
    }

    
    public int size () {
	return images.size ();
    }


}
