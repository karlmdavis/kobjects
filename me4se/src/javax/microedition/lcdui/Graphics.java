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

import javax.microedition.midlet.*;

public class Graphics {
   
    public static final int HCENTER = 1;
    public static final int VCENTER = 2;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int TOP = 16;
    public static final int BOTTOM = 32;

    public static final int BASELINE = 64;
    public static final int SOLID =  0;
    public static final int DOTTED = 1;
   
	public int translateX = 0;
	public int translateY = 0;
	
	public int strokeStyle = SOLID;

    java.awt.Graphics g;
    Font font;
  
    Graphics (java.awt.Graphics g) {
	this.g = g;
	if (g== null) throw new RuntimeException ("g==null");
	setFont (Font.DEFAULT_FONT);
    }


    public void drawArc (int x, int y, int w, int h, int sa, int aa) {
	g.drawArc (x, y, w, h, sa, aa);
    }
    
    public void drawChars (char [] chars, int ofs, int len, int x, int y, int align) {
	drawString (new String (chars, ofs, len), x, y, align);
    }
    
    

   public void drawImage (Image img, int x, int y, int align) {
	
	if( align == 0 )
		align = TOP | LEFT;
		
	switch (align & (TOP|BOTTOM|BASELINE|VCENTER)) {
	case TOP: break;
	case BOTTOM: y -= img.getHeight (); break;
	case VCENTER: y -= img.getHeight () / 2; break;
	default: throw new IllegalArgumentException ();
	} 

	switch (align & (LEFT|RIGHT|HCENTER)) {
	case LEFT: break;
	case RIGHT: x -= img.getWidth (); break;
	case HCENTER: x -= img.getWidth ()/2; break;
	default: throw new IllegalArgumentException ();
	} 

	g.drawImage (img.image, x, y, null);
    }     

    
    public void drawLine (int x0, int y0, int x1, int y1) {
	g.drawLine (x0, y0, x1, y1);
    }


    public void drawString (String text, int x, int y, int align) {
	
	if( align == 0 )
		align = TOP | LEFT;
	
	java.awt.FontMetrics fm = g.getFontMetrics ();

	switch (align & (TOP|BOTTOM|BASELINE|VCENTER)) {
	case TOP: y += fm.getAscent (); break;
	case BOTTOM: y -= fm.getDescent (); break;
	case BASELINE: break;
	case VCENTER: y = y + fm.getAscent () - fm.getHeight () / 2; break;
	default: throw new IllegalArgumentException ();
	} 

	switch (align & (LEFT|RIGHT|HCENTER)) {
	case LEFT: break;
	case RIGHT: x -= fm.stringWidth (text); break;
	case HCENTER: x -= fm.stringWidth (text)/2; break;
	default: throw new IllegalArgumentException ();
	} 

	g.drawString (text, x, y);
    } 


    public void drawRect (int x, int y, int w, int h) {
	g.drawRect (x, y, w, h);
    }


    public void drawRoundRect (int x, int y, int w, int h, int r1, int r2) {
	g.drawRoundRect (x, y, w, h, r1, r2);
    }

    public void fillArc (int x, int y, int w, int h, int sa, int aa) {
	g.fillArc (x, y, w, h, sa, aa);
    }

    public void fillRect (int x, int y, int w, int h) {
	g.fillRect (x, y, w, h);
    }
    

    public void fillRoundRect (int x, int y, int w, int h, int r1, int r2) {
	g.fillRoundRect (x, y, w, h, r1, r2);
    }

    public Font getFont () {
	return font;
    }

    public int getClipX () {
	java.awt.Rectangle r = g.getClipBounds ();
	return r == null ? 0 : r.x;
    }

    public int getClipY () {
	java.awt.Rectangle r = g.getClipBounds ();
	return r == null ? 0 : r.y;
    }


    public int getClipWidth () {
	java.awt.Rectangle r = g.getClipBounds ();
	return r == null ? ApplicationManager.manager.canvasWidth : r.width;
    }

    public int getClipHeight () {
	java.awt.Rectangle r = g.getClipBounds ();
	return r == null ?  ApplicationManager.manager.canvasHeight : r.height;
    }


    public int getColor () {
	return g.getColor ().getRGB ();
    }

    
    public void setFont (Font font) {
	g.setFont (font.font);
	this.font = font;
    }
    

    public void translate (int x, int y) {
	translateX += x;
	translateY += y;
	
	g.translate (x, y);
    }

    public void clipRect (int x, int y, int w, int h) {
	g.clipRect (x, y, w, h);
    }

    public void setClip (int x, int y, int w, int h) {
	g.setClip (x, y, w, h);
    }
    

    public void setColor (int color) {
	g.setColor (new java.awt.Color (color));
    }

    public void setColor (int cr, int cg, int cb) {
	g.setColor (new java.awt.Color (cr, cg, cb));
    }



    public void setGrayScale (int gsc) {
	setColor (gsc | (gsc << 8) | (gsc << 16)); 
    }
	
	public void drawSubstring(String str,
                          int offset,
                          int len,
                          int x,
                          int y,
                          int anchor)
	{
		drawString( str.substring( offset , offset + len - 1 ) , x , y , anchor );
}
	
	public void drawChar(char character,
                     int x,
                     int y,
                     int anchor)
	{
		char characters[] = new char[1];
		characters[0] = character;
		drawString( new String( characters ) , x , y , anchor );
	}
	
	public int getBlueComponent() 
	{
		return g.getColor().getBlue();
    }

	public int getGreenComponent() 
	{
		return g.getColor().getGreen();
    }

	public int getRedComponent() 
	{
		return g.getColor().getRed();
    }

	public int getGrayScale()
	{
		return getRedComponent();
	}	

	public int getTranslateX()
	{
		return translateX;
}
	
	public int getTranslateY()
	{
		return translateY;
	}	

	public void setStrokeStyle(int style)
	{
		//TBD does nothing at the moment
		strokeStyle = style;
	}
	
	public int getStrokeStyle()
	{
		//TBD does nothing at the moment
		return strokeStyle;
	}
	
}

