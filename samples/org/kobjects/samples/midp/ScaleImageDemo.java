package org.kobjects.samples.midp;

// (C) 2002 by Stefan Haustein 
// Rolandstrasse 27, D-46045 Oberhausen, Germany
// All rights reserved.
//
// For licensing details, please refer to the file "license.txt",
// distributed with this file.

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;
import javax.microedition.lcdui.*;

/**
 * @author Stefan Haustein */

public class ScaleImageDemo extends MIDlet {
	
    public ScaleImageDemo() {
    }

    protected void destroyApp(boolean unconditional) {}

    protected void pauseApp() {}

    protected void startApp()
        throws MIDletStateChangeException {

		Form form = new Form ("ScaleDemo");
		Image img = Image.createImage(10, 10);
		Graphics g = img.getGraphics();
			
		g.drawLine (0, 0, 9, 9);
		g.drawLine (0, 1, 9, 10);

		g.drawLine (0, 9, 9, 0);
		g.drawLine (1, 9, 10, 0);
		
		form.append(Image.createImage(img)); // immutable vers.
		
		Image big = org.kobjects.lcdui.ScaleImage.scaleImage(img, 30, 30);

		form.append(Image.createImage(big)); // immutable vers.

		Image small = org.kobjects.lcdui.ScaleImage.scaleImage(img, 5, 5);

		form.append(Image.createImage(small)); // immutable vers.

		
		Display.getDisplay(this).setCurrent(form);		

    }

}
