// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API Complete
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


public class ImageItem extends Item {

    public static final int LAYOUT_DEFAULT = 0;
    public static final int LAYOUT_LEFT = 1;
    public static final int LAYOUT_RIGHT = 2;
    public static final int LAYOUT_CENTER = 3;
    public static final int NEWLINE_BEFORE = 0x0100;
    public static final int NEWLINE_AFTER = 0x0200;


    Image image;
    Wrapper wrapper = new Wrapper ();
    String alt;
    int layout;

    class Wrapper extends java.awt.Canvas {
	public void paint (java.awt.Graphics g) {
	    g.drawImage (image.image, 0, 0, this);
	}

    }


    public ImageItem (String label, Image img, int layout, String alt) {
	super (label);
	
	setImage (img);
	setLayout (layout);
	this.alt = alt;
	wrapper.setSize (img.getWidth (), img.getHeight ());
    }
    

    public String getAltText () {
	return alt;
    }

    public Image getImage () {
	return image;
    }


    java.awt.Component getField () {
	return wrapper;
    }

    public int getLayout () {
	return layout;
    }



    public void setAltText (String s) {
	alt = s;
    }

    public void setImage (Image img) {
	if (img.mutable)
	    throw new IllegalArgumentException ();

	this.image = img;
	validate ();
    }


    public void setLayout (int l) {
	if ((l & ~0x0303) != 0) 
	    throw new IllegalArgumentException ();
	
	layout = l;
    }
}
