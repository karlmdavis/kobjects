package javax.microedition.lcdui;

// ME4SE - A MicroEdition Emulation for J2SE 
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors:
//
// STATUS: API complete, but ticker does not scroll yet.
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



public abstract class Screen extends Displayable {

    java.awt.Label titleLabel;
    java.awt.Label tickerLabel;
    java.awt.Panel topPanel = 
	new java.awt.Panel (new java.awt.BorderLayout ());

    Ticker ticker;

    Screen (String title) {
	setTitle (title);
	panel.add ("North", topPanel);
    }

    
    public String getTitle () {
	return titleLabel == null
	    ? null
	    : titleLabel.getText ();
    }


    public void setTitle (String title) {
	if (title == null) {
	    if (titleLabel != null) {
		topPanel.remove (titleLabel);
		titleLabel = null;
	    }
	}
	else {
	    if (titleLabel == null) {
		titleLabel = new java.awt.Label (title);
		topPanel.add (titleLabel, java.awt.BorderLayout.SOUTH);
	    }
	    else titleLabel.setText (title);
	}

	panel.validate ();
    }


    public void setTicker (Ticker ticker) {
	this.ticker = ticker;

	if (ticker == null) {
	    if (tickerLabel != null) {
		topPanel.remove (tickerLabel);
		tickerLabel = null;
	    }
	}
	else {
	    if (tickerLabel == null) {
		tickerLabel = new java.awt.Label (ticker.getString ());
		topPanel.add (tickerLabel, java.awt.BorderLayout.NORTH);
	    }
	    else tickerLabel.setText (ticker.getString ());
	}
    }


    public Ticker getTicker () {
	return ticker;
    }
}
