package javax.microedition.lcdui;

import java.io.*;
import java.awt.image.*;
import java.util.Vector;

import javax.microedition.midlet.*;

public class Image {
    
  

    java.awt.Image image;
    boolean mutable;
    static java.awt.Component helper = new java.awt.Panel ();
    
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
