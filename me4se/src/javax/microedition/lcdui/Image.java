// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: Geoff Hubbard
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

import java.io.*;
import java.awt.image.*;
import java.util.Vector;

import javax.microedition.midlet.*;

public class Image {
    
  

    java.awt.Image image;
    boolean mutable;
    java.awt.Component helper = new java.awt.Panel ();
    
    Image (int w, int h) {
	image = ApplicationManager.manager.displayContainer.createImage 
	    (w, h);
	if (image == null) throw new RuntimeException ("Image not loaded");
	mutable = true;
    }

    
    Image (InputStream is) {
       	this.image = java.awt.Toolkit.getDefaultToolkit ().createImage 
	    (new com.sixlegs.image.png.PngImage (is));
    }


    public static Image createImage (int w, int h) {
	return new Image (w, h);
    }

    public static Image createImage (byte [] data, int start, int len) {
	return new Image (new ByteArrayInputStream (data, start, len));
    }


    public static Image createImage (String name) throws IOException {

	InputStream is = Image.class.getResourceAsStream (name);
	if (is == null) throw new IOException 
	    ("Resosurce '"+name+"' not found!");

	return new Image (is);
    }


    public static Image createImage ( Image source ) {
	if (source == null) 
	    throw new NullPointerException();
   
	if (!source.isMutable()) 
	    return source; 

	Image copy = new Image (source.getWidth(), source.getHeight());
	copy.getGraphics().drawImage 
	    (source, 0, 0, Graphics.TOP | Graphics.LEFT);
   
	copy.mutable = false;
	return copy;
    }

    
    public Graphics getGraphics () {
	if (!mutable) throw new IllegalStateException ();
	return new Graphics (image.getGraphics ());
    }


    public int getWidth () {
	return image.getWidth (null);
    }

    public int getHeight () {
	return image.getHeight (null);
    }

    public boolean isMutable () {
	return mutable;
    }
}
