// kObjects / WordWrap
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: 
//
// License: LGPL
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public License
// as published by the Free Software Foundation; either version 2.1 of
// the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful, but
// WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
// USA


package org.kobjects.lcdui;

import javax.microedition.lcdui.*;

public class WordWrap {

    Font font;
    int width;
    int maxWidth;
    String txt;
    int pos;

    public WordWrap (Font font, String txt, int width) {
	
	this.font = font;
	this.txt = txt;
	this.width = width;
    }
    

    /** returns -1 if no text is left */

    public int next () {
	
	int i = pos;
	int len = txt.length ();

	if (pos >= len) return -1;
	
	int start = pos;
	
	while (true) {
	    while (i < len && txt.charAt (i) > ' ')
		i++;

	    int w = font.stringWidth (txt.substring (start, i));
	    if (pos == start  || w <= width) {
		if (w > maxWidth) maxWidth = w;
		pos = i;
	    }

	    if (w > width || i >= len || txt.charAt(i) == '\n') break;
	    i++;
	}

	return pos >= len ? pos : ++pos;
    }


    public int getMaxWidth () {
	return maxWidth;
    }
}
