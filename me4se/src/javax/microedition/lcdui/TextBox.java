// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS:
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

public class TextBox extends Screen {

    java.awt.TextArea box = new java.awt.TextArea 
	("", 20,20, java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY);
    int maxSize;
    int constraints;

    public TextBox (String title, String text, int maxSize, int constraints) {
	super (title);
	box.setText (text);
	panel.add ("Center", box);
	this.maxSize = maxSize;
	this.constraints = constraints;
    }

    public void setString (String text) {
	box.setText (text);
    }

    public int getMaxSize () {
	return maxSize;
    }


    public int setMaxSize (int maxSize) {
	this.maxSize = maxSize;
    return maxSize;
    }


    public String getString () {
	return box.getText ();
    }

}
