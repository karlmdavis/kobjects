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

public class Font {

    static final Font DEFAULT_FONT = new Font (0,0,0);
    
    java.awt.Font font;
    java.awt.FontMetrics fontMetrics;

    public static final int STYLE_PLAIN = 0;
    public static final int STYLE_BOLD = 1;
    public static final int STYLE_ITALIC = 2;
    public static final int STYLE_UNDERLINED = 4;

    public static final int SIZE_SMALL = 8;
    public static final int SIZE_MEDIUM = 0;
    public static final int SIZE_LARGE = 16;

    public static final int FACE_MONOSPACE = 32;
    public static final int FACE_PROPORTIONAL = 64;
    public static final int FACE_SYSTEM = 0;

    
    int face;
    int style;
    int size;


    Font (int face, int style, int size) {
	this.face = face;
	this.style = style;
	this.size = size;

	String name;
	int points = javax.microedition.midlet.ApplicationManager.manager.displayContainer.getFont ().getSize ();
	int awtStyle = 0;

	switch (face) {
	case FACE_SYSTEM: name = "SansSerif"; break;
	case FACE_PROPORTIONAL: name = "Serif"; break;
	case FACE_MONOSPACE: name = "Monospaced"; break;
	default: throw new IllegalArgumentException ();
	}

	switch (style & 3) {
	case STYLE_PLAIN: awtStyle = java.awt.Font.PLAIN; break;
	case STYLE_BOLD: awtStyle = java.awt.Font.BOLD; break;
	case STYLE_ITALIC: awtStyle = java.awt.Font.BOLD; break;
	case STYLE_BOLD|STYLE_ITALIC: awtStyle = java.awt.Font.BOLD 
			      | java.awt.Font.ITALIC; break;
	}

	switch (size) {
	case SIZE_MEDIUM: break;
	case SIZE_SMALL: points -= 2; break;
	case SIZE_LARGE: points += 2; break;
	default: throw new IllegalArgumentException ();
	}
	
	this.font = new java.awt.Font (name, awtStyle, points);
	this.fontMetrics = java.awt.Toolkit.getDefaultToolkit ().getFontMetrics (font);
    }

    
    public int charWidth (char c) {
	return fontMetrics.charWidth (c);
    }

    public int charsWidth (char [] c, int ofs, int len) {
	return fontMetrics.charsWidth (c, ofs, len);
    }


    public int getBaselinePosition () {
	return fontMetrics.getAscent ();
    }


    public static Font getDefaultFont () {
	return DEFAULT_FONT;
    }

    public int getFace () {
	return face;
    }

    public static Font getFont (int face, int style, int size) {
	return new Font (face, style, size);
    }


    public int getHeight () {
	return fontMetrics.getHeight ();
    }


    public int getSize () {
	return size;
    }

    public int getStyle () {
	return style;
    }


    public boolean isBold () {
	return (style & STYLE_BOLD) != 0;
    }

    public boolean isUnderlined () {
	return (style & STYLE_UNDERLINED) != 0;
    }

    public boolean isItalic () {
	return (style & STYLE_ITALIC) != 0;
    }
    public boolean isPLAIN () {
	return style == 0;
    }


    public int stringWidth (String s) {
	return fontMetrics.stringWidth (s);
    }


    public int substringWidth (String s, int ofs, int len) {
	return fontMetrics.stringWidth (s.substring (ofs, ofs+len));
    }
}




