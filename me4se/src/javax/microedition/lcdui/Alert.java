// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete
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

public class Alert extends Screen {

    static final Command ALERT_COMMAND = new Command ("Ok", Command.OK, -9999999);
    public static final int FOREVER = -2;

    int timeout;
    Image image;
    AlertType type;
    java.awt.TextArea text = new java.awt.TextArea 
	("", 20,20, java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY);

    Displayable next;
    
    
    public Alert (String title, String alertText, Image image, AlertType type) {
	this (title);
	setString (alertText);
	this.image = image;
	this.type = type;
    }


    public Alert (String title) {
	super (title);
	panel.add ("Center", text);
	addCommand (ALERT_COMMAND);
    }

    void sendCommand (Command cmd) {
  	if (cmd == ALERT_COMMAND)
	    display.setCurrent (next);
	else 
	    super.sendCommand (cmd);
    }
    
    
    public Image getImage () {
	return image;
    }


    public String getString () {
	return text.getText ();
    }

    public int getTimeout () {
	return timeout;
    }


    public int getDefaultTimeout () {
	return FOREVER;
    }

    public void setTimeout (int time) {
	this.timeout = time;
    }


    public void setImage (Image image) {
	this.image = image;
    }


    public void setString (String text) {
	this.text.setText (text);
    }


    public void setType (AlertType type) {
	this.type = type;
    }
}


