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
import javax.microedition.midlet.*;

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
    java.awt.Panel moreCommandPanel = new java.awt.Panel (new java.awt.GridLayout (5,1));

    java.awt.Button button = new java.awt.Button ();

    java.awt.Button button2 = new java.awt.Button ();
	java.awt.Button escButton = new java.awt.Button( "Esc" );

    Command button2Command = null;
	boolean displayCommandListScreen;
    java.util.Vector commandButtons = new Vector ();
	Helper h;
    
    CommandListener commandListener;
    java.util.Vector commands = new Vector ();
    Command buttonCommand;
    Display display;


    class Helper implements ActionListener/*, ItemListener*/ {
	

/*	public void itemStateChanged (ItemEvent ev) {
	    if (ev.getStateChange () == ItemEvent.SELECTED) {
		int i = choice.getSelectedIndex ();
		if (i > 0) {
		    sendCommand ((Command) commands.elementAt (i-1));
		    choice.select (0);
		}
		}}*/

		public void actionPerformed (ActionEvent ev) 
		{
			int index = -1;
		    if( ev.getSource() == button )		
		    {
			    if (buttonCommand != null)
					sendCommand (buttonCommand);
			}
		    else if( ev.getSource() == button2 )		
		    {
		    	if( displayCommandListScreen )
		    	{
					ApplicationManager.manager.displayContainer.removeAll ();
					ApplicationManager.manager.displayContainer.add( "Center" , moreCommandPanel );
					ApplicationManager.manager.displayContainer.validate ();					
					ApplicationManager.manager.displayContainer.repaint ();		    			
		    	}
		    	else
		    	{
				    if (button2Command != null)
						sendCommand (button2Command);
				}
			}
		    else if( ev.getSource() == escButton )		
		    {
				ApplicationManager.manager.displayContainer.removeAll ();
				ApplicationManager.manager.displayContainer.add( "Center" , panel );
				ApplicationManager.manager.displayContainer.validate ();					
				ApplicationManager.manager.displayContainer.repaint ();		    			
			}
			else if( ( index = commandButtons.indexOf( ev.getSource() ) ) >= 0 )
			{
				ApplicationManager.manager.displayContainer.removeAll ();
				ApplicationManager.manager.displayContainer.add( "Center" , panel );
				ApplicationManager.manager.displayContainer.validate ();					
				ApplicationManager.manager.displayContainer.repaint ();		    			
				sendCommand ( (Command) commands.elementAt( index ) );
			}			
			else
			{
	    }		
	}
    }

    Displayable () {
	panel.add ("South", commandPanel);
	commandPanel.add (button);
	commandPanel.add (button2);


	h = new Helper ();
	button.addActionListener (h);

	button2.addActionListener (h);
	setButtonCommands();

    }

    void sendCommand (Command cmd) {
	if (commandListener != null) 
	    commandListener.commandAction 
		(cmd, Displayable.this);
    }

 



	void setButtonCommands()
	{
		buttonCommand = null;
		button2Command = null;
		button.setVisible(false);
		button2.setVisible(false);
		displayCommandListScreen = false;
		commandButtons.removeAllElements();
		if( commands.size() > 0 )
		{
			buttonCommand = (Command) commands.elementAt( 0 );
			button.setVisible(true);			
			button.setLabel( buttonCommand.getLabel() );
		}
		if( commands.size() == 2 )
		{
			button2Command = (Command) commands.elementAt( 1 );
			button2.setVisible(true);			
			button2.setLabel( button2Command.getLabel() );
		}
		if( commands.size() > 2 )
		{
			displayCommandListScreen = true;
			button2.setVisible(true);			
			button2.setLabel( "Options" );
			moreCommandPanel.removeAll();
			commandButtons.addElement( button );
			for( int i = 1 ; i < commands.size() ; i ++ )
			{
				Command curCmd = (Command) commands.elementAt( i );
				java.awt.Button extraButton = new java.awt.Button( curCmd.getLabel() );
				commandButtons.addElement( extraButton );
				extraButton.addActionListener (h);
				moreCommandPanel.add( "North" , extraButton );		
			}
			escButton.addActionListener(h);			
			moreCommandPanel.add( "South" , escButton );							
	}
    }

    public synchronized void addCommand (Command cmd) 
    {

		if (commands.indexOf (cmd) != -1) 
			return;	
		for( int i=0 ; i< commands.size() ; i++ )
		{
			Command curCmd = (Command) commands.elementAt( i );
			if( cmd.getPriority () < curCmd.getPriority() )
			{
				commands.insertElementAt( cmd , i );
				setButtonCommands();
				return;
			}				
		}
		commands.insertElementAt( cmd , commands.size() );
		setButtonCommands();

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

		setButtonCommands();

    }
    
}

