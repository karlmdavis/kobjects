package javax.microedition.lcdui;

public class StringItem extends Item {

    java.awt.Label textLabel = new java.awt.Label ();
    String text;

    public StringItem (String label, String text) {
	super (label);
	setText (text);
    }

    public void setText (String text) {
	this.text = text;
	textLabel.setText (text == null ? null : text.replace ('\n', ' ')); 
    }

    public String getText () {
	return text;
    }

    java.awt.Component getField () {
	return textLabel;
    }
}
