package javax.microedition.lcdui;

public class Alert extends Screen{

    static final Command ALERT_COMMAND = new Command ("Ok", Command.OK, -9999999);
    public static final int FOREVER = -2;

    Image image;
    java.awt.TextArea text = new java.awt.TextArea 
	("", 20,20, java.awt.TextArea.SCROLLBARS_VERTICAL_ONLY);

    Displayable next;
    


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
    

    public void setTimeout (int time) {
    }


    public void setImage (Image image) {
	this.image = image;
    }

    public void setString (String text) {
	this.text.setText (text);
    }
}


