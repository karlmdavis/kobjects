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

import java.util.*;
import java.awt.event.*;

public abstract class Displayable {

    java.awt.Panel panel = new java.awt.Panel (new java.awt.BorderLayout ()) {
	    public void paintAll (java.awt.Graphics g) {
		Vector toRun = null;
		
		if (display.callSerially.size () > 0) {
		    toRun = display.callSerially;
		    display.callSerially = new Vector ();
		}
		
		super.paintAll (g);
		
		if (toRun != null) 
		    for (int i = 0; i < toRun.size (); i++) 
			((Runnable) toRun.elementAt (i)).run ();
		
	    }
	};
	    
    java.awt.Panel commandPanel = new java.awt.Panel (new java.awt.GridLayout (1,2));
    java.awt.Button button = new java.awt.Button ();
    java.awt.Choice choice = new java.awt.Choice ();
    
    CommandListener commandListener;
    java.util.Vector commands = new Vector ();
    Command buttonCommand;
    Display display;


    class Helper implements ActionListener, ItemListener {
	

	public void actionPerformed (ActionEvent ev) {
	    if (buttonCommand != null)
		sendCommand (buttonCommand);
	}

	public void itemStateChanged (ItemEvent ev) {
	    if (ev.getStateChange () == ItemEvent.SELECTED) {
		int i = choice.getSelectedIndex ();
		if (i > 0) {
		    sendCommand ((Command) commands.elementAt (i-1));
		    choice.select (0);
		}
	    }		
	}
    }

    Displayable () {
	panel.add ("South", commandPanel);
	commandPanel.add (button);
	commandPanel.add (choice);


	Helper h = new Helper ();
	button.addActionListener (h);
	choice.addItemListener (h);
	choice.add ("Menu");
    }

    void sendCommand (Command cmd) {
	if (commandListener != null) 
	    commandListener.commandAction 
		(cmd, Displayable.this);
    }

 

    void checkButtonCommand (Command cmd) {
	if (buttonCommand == null 
	    || cmd.getPriority () < buttonCommand.getPriority ()) {

	    button.setLabel (cmd.getLabel ());
	    buttonCommand = cmd;
	}
    }



    public void addCommand (Command cmd) {
	if (commands.indexOf (cmd) != -1) return;

	commands.addElement (cmd);
	choice.add (cmd.getLabel ());
	checkButtonCommand (cmd);
    }
    
    void notifyDisplayed () {
    }


    public boolean isShown () {
	return display != null && display.current == this;
    }
    

    public void setCommandListener (CommandListener commandListener) {
	this.commandListener = commandListener;
    }
	

   
    public void removeCommand (Command cmd) {
	int idx = commands.indexOf (cmd);
	if (idx == -1) return;
	commands.removeElementAt (idx);
	choice.remove (idx+1);
	if (buttonCommand == cmd) 
	    buttonCommand = null;
	    for (int i = 0; i < commands.size (); i++) 
		checkButtonCommand ((Command) commands.elementAt (i)); 
    }
    
}

