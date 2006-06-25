/*
 * Created on 18.11.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.me4se.browser;

import java.io.*;
import java.util.Vector;

import javax.microedition.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

import org.kobjects.xml.*;

/**
 * @author haustein
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class WebScreen extends Form implements CommandListener, Runnable, ItemCommandListener {

	String title;
	String url;
	MIDlet owner;
	Display display;
	Displayable quit;
	Vector back = new Vector();	


	TextBox urlBox = new TextBox("Goto URL", "http://kobjects.org/e.wml", 1024, TextField.URL);

	public static final Command GO = new Command("Go", Command.SCREEN, 0);
	public static final Command QUIT = new Command("Quit", Command.EXIT, 0);
	public static final Command OK = new Command("Ok", Command.OK, 0);
	public static final Command CANCEL = new Command("Cancel", Command.CANCEL, 0);
	public static final Command BACK = new Command("Back", Command.BACK, 0);

	/**
	 * Constructor for a displayable that is able to display simple web content.
	 * 
	 * @param owner The MIDlet that owns this form.
	 * @param quit May be a Displayable that is displayed when the browser is left. If null, notifyDestroyed is called when the browser is left.
	 * @param title The Form title
	 */

	public WebScreen(MIDlet owner, String title, Displayable quit) {
		super(title);
		this.display = Display.getDisplay(owner);
		this.owner = owner;
		this.title = title;
		this.quit = quit;
		setCommandListener(this);
		addCommand(GO);
		addCommand(QUIT);
		addCommand(BACK);
		urlBox.addCommand(OK);
		urlBox.addCommand(CANCEL);
		urlBox.setCommandListener(this);
	}

	public void run() {
		try {
			HttpConnection con = (HttpConnection) Connector.open(url);
			String enc = con.getEncoding();

			Reader r =
				enc == null
					? new InputStreamReader(con.openInputStream())
					: new InputStreamReader(con.openInputStream(), enc);

			XmlReader xr = new XmlReader(r);
			load(xr);
			r.close();
			con.close();
		}
		catch (Exception e) {
			append(new StringItem("Error loading URL:", url));
			append(new StringItem("Error Msg:", e.toString()));
			e.printStackTrace();
		}
	}

	void load(XmlReader xr) throws IOException {

		String link = null;

		while (xr.next() != XmlReader.END_DOCUMENT) {
			int type = xr.getType();
			if (type == XmlReader.TEXT) {
				String text = xr.getText().trim();
				if (text.length() == 0);
				else if (link == null) {
					append(xr.getText().trim());
				}
				else {
					StringItem si = new StringItem(null, text, StringItem.HYPERLINK);
					si.addCommand(new Command("Open", link, Command.ITEM, 0));
					si.setItemCommandListener(this);
					append(si);
				}
			}
			else if (type == XmlReader.START_TAG) {
				String name = xr.getName().toLowerCase();
				if (name.equals("a")) {
					link = xr.getAttributeValue("href");
				}
			}
			else if (type == XmlReader.END_TAG) {
				String name = xr.getName().toLowerCase();
				if (name.equals("a"))
					link = null;
			}
		}
	}

	void openRelative(String newUrl) {
		if (!newUrl.startsWith("http://")) {
			int cut = url.lastIndexOf("/");
			if (cut == -1)
				cut = url.length();
			newUrl = url.substring(0, cut) + "/" + newUrl;
		}
		openUrl(newUrl);
	}

	/** Overwrite this method to intercept .jad */

	public void openUrl(String newUrl) {
		
		back.addElement(url);
//		if(back.size() == 1)
//			addCommand(BACK);
		//else 
		
		if(back.size() == 10)
			back.remove(0);
				
		this.url = newUrl;
		deleteAll();
		new Thread(this).start();
	}

	public void commandAction(Command cmd, Displayable d) {
		if (cmd == GO) {
			display.setCurrent(urlBox);
		}
		else if (cmd == OK) {
			display.setCurrent(this);
			openRelative(urlBox.getString());
		}
		else if (cmd == CANCEL) {
			display.setCurrent(this);
		}
		else if (cmd == QUIT) {
			if (quit != null)
				display.setCurrent((Displayable) quit);
			else
				owner.notifyDestroyed();
		}
		else if(cmd == BACK){
			int idx = back.size()-2;
			if(idx >= 0){
				String bk = (String) back.elementAt(idx);
				back.remove(idx);
				back.remove(idx);
				openUrl(bk);
			}
		}
	}

	public void commandAction(Command command, Item item) {
		if (command.getLabel().equals("Open")) {
			openRelative(command.getLongLabel());
		}
	}

}
