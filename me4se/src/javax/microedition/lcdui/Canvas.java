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

import java.awt.event.*;
import javax.microedition.midlet.*;

public abstract class Canvas extends Displayable {

    public static final int DOWN = 6;
    public static final int LEFT = 2;
    public static final int RIGHT = 5;
    public static final int UP = 1;
    public static final int FIRE = 8;
    public static final int GAME_A = 9;
    public static final int GAME_B = 10;
    public static final int GAME_C = 11;
    public static final int GAME_D = 12;

    public static final int KEY_NUM0 = 48;
    public static final int KEY_NUM1 = 49;
    public static final int KEY_NUM2 = 50;
    public static final int KEY_NUM3 = 51;
    public static final int KEY_NUM4 = 52;
    public static final int KEY_NUM5 = 53;
    public static final int KEY_NUM6 = 54;
    public static final int KEY_NUM7 = 55;
    public static final int KEY_NUM8 = 56;
    public static final int KEY_NUM9 = 57;

    public static final int KEY_POUND = '#';
    public static final int KEY_STAR = '*';

    boolean repaintPending;
    Object repaintLock = new Object ();

    boolean request;
    java.awt.Image offscreen;
    java.awt.Dimension offscreenSize;
    boolean first;
    
    class Wrapper extends java.awt.Canvas 
	implements KeyListener, MouseListener, MouseMotionListener {
	
	Wrapper () {
	    addKeyListener (this);
	    addMouseListener (this);
	    addMouseMotionListener (this);
	}


	public void update (java.awt.Graphics g) {
	    paint (g);
	}
	
	public void paint (java.awt.Graphics g) {
	   
	    try {
		synchronized (repaintLock) {
		    
		    repaintPending = false; 
		    
		    if (request) {
			requestFocus ();
			request = false;
		    }
		    
		    java.awt.Dimension d = getSize ();
		
		    if (offscreen == null ||
			!offscreenSize.equals (d)) {
			offscreenSize = d;
			offscreen = createImage (d.width, d.height);
		    }
		
		    Canvas.this.paint (new Graphics (offscreen.getGraphics ()));
		    
		    g.drawImage (offscreen, 0, 0, null);
		    
		    repaintLock.notify ();
		}
	    }
	    catch (Exception e) {
	    }
	}

	public int decode (KeyEvent ev) {
	    char c = ev.getKeyChar ();
	    return (c == 0 || c == KeyEvent.CHAR_UNDEFINED)
		? -ev.getKeyCode ()
		: (int) c;
	}

  	       
	public boolean isFocusTraversable () {
	    return true;
	}

	public void mouseClicked (MouseEvent ev) {
	}

	public void mouseEntered (MouseEvent ev) {
	}

	public void mouseExited (MouseEvent ev) {
	}


	public void mouseDragged (MouseEvent ev) {
	    pointerDragged (ev.getX (), ev.getY ());
	}


	public void mouseMoved (MouseEvent ev) {
	}

	public void mousePressed (MouseEvent ev) {	    
	    pointerPressed (ev.getX (), ev.getY ());
	}


	public void mouseReleased (MouseEvent ev) {
	    pointerReleased (ev.getX (), ev.getY ());
	}


	public void keyPressed (KeyEvent ev) {
	    
	    Canvas.this.keyPressed (decode (ev));
	    first = true;
	}

	public void keyReleased (KeyEvent ev) {
	    Canvas.this.keyReleased (decode (ev));
	}

	public void keyTyped (KeyEvent ev) {
	    if (first) first = false;
	    else Canvas.this.keyRepeated (decode (ev));
	}
    }

    java.awt.Canvas wrapper = new Wrapper ();


    public Canvas () {
	panel.add ("Center", wrapper);
    }

    protected abstract void paint (Graphics g);

    public int getGameAction (int keyCode) {
	switch (keyCode) {
	case -KeyEvent.VK_UP: return UP;
	case -KeyEvent.VK_DOWN: return DOWN;
	case -KeyEvent.VK_LEFT: return LEFT;
	case -KeyEvent.VK_RIGHT: return RIGHT;
	case 32: return FIRE;
	case 'a': 
	case 'A': return GAME_A;
	case 's': 
	case 'S': return GAME_B;
	case 'd': 
	case 'D': return GAME_C;
	case 'f': 
	case 'F': return GAME_D;
	default: return 0;
	}
    }


    public int getKeyCode (int game) {
	switch (game) {
	case UP: return -KeyEvent.VK_UP;
	case DOWN: return -KeyEvent.VK_DOWN;
	case LEFT: return -KeyEvent.VK_LEFT;
	case RIGHT: return -KeyEvent.VK_RIGHT;
	case FIRE: return 32;
	case GAME_A: return 'a';
	case GAME_B: return 's';
	case GAME_C: return 'd';
	case GAME_D: return 'f';
	}
	throw new IllegalArgumentException ();
    }

    public String getKeyName (int keyCode) {
	return (keyCode > 0) 
	    ? (""+(char) keyCode) 
	    :  KeyEvent.getKeyText (-keyCode);
    }

    public int getHeight () {
	if (ApplicationManager.manager.canvasHeight == 0) 
	    ApplicationManager.manager.canvasHeight = wrapper.getSize ().height;
	return ApplicationManager.manager.canvasHeight;
    }



    public int getWidth () {
	if (ApplicationManager.manager.canvasWidth == 0) 
	    ApplicationManager.manager.canvasWidth = wrapper.getSize ().width;
	
	return ApplicationManager.manager.canvasWidth;
    }

  
    public boolean hasPointerEvents () {
	return true;
    }


    public boolean hasPointerMotionEvents () {
	return true;
    }

    public boolean hasRepeatEvents () {
	return true;
    }




    public boolean isDoubleBuffered () {
	return true;
    }

    
    protected void hideNotify () {
    }


    protected void keyPressed (int code) {
    }

    protected void keyReleased (int code) {
    }

    protected void keyRepeated (int code) {
    }

    protected void pointerDragged (int x, int y) {
    }

    protected void pointerPressed (int x, int y) {
    }

    protected void pointerReleased (int x, int y) {
    }


    public void repaint () {
	repaintPending = true;
	wrapper.repaint ();
    }


    void notifyDisplayed () {
	 request = true;
    }

    void sendCommand (Command cmd) {
	request = true;
	super.sendCommand (cmd);
    }

    public void serviceRepaints () {
	if (repaintPending) 
	    synchronized (repaintLock) {
		java.awt.Graphics g = wrapper.getGraphics ();
		if (g != null) wrapper.paint (g);
	    }
    }



    public void repaint (int x, int y, int w, int h) {
	repaintPending = true;
	wrapper.repaint (x, y, w, h);
    }


    protected void showNotify () {
    }
}
