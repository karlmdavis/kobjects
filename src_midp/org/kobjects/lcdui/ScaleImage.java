package org.kobjects.lcdui;

// (C) 2002 by Stefan Haustein 
// Rolandstrasse 27, D-46045 Oberhausen, Germany
// All rights reserved.
//
// For licensing details, please refer to the file "license.txt",
// distributed with this file.

import javax.microedition.lcdui.*;

/**
 * @author Stefan Haustein */

public class ScaleImage {


	public static Image scaleImage (Image src, int dstW, int dstH) {
		int srcW = src.getWidth();
		int srcH = src.getHeight();
		
		Image tmp = Image.createImage(dstW, srcH);
		Graphics g = tmp.getGraphics();
		
		int delta = (srcW << 16) / dstW;
		int pos = delta/2;
		
		for (int x = 0; x < dstW; x++) {
			g.setClip(x, 0, 1, srcH);
			g.drawImage(src, x - (pos >> 16), 0, Graphics.LEFT | Graphics.TOP);
			pos += delta;
		}
		
		Image dst = Image.createImage(dstW, dstH);
		g = dst.getGraphics();
		
		delta = (srcH << 16) / dstH;
		pos = delta/2;
		
		for (int y = 0; y < dstH; y++) {
			g.setClip(0, y, dstW, 1);
			g.drawImage(tmp, 0, y - (pos >> 16), Graphics.LEFT | Graphics.TOP);
			pos += delta;	
		}
		
		return dst;		
	}


}
