package javax.microedition.lcdui;

public class StringItem extends Item {

    java.awt.Container textPanel = new java.awt.Panel (new java.awt.GridLayout (0, 1, 0, 0));
    String text;

    public StringItem (String label, String text) {
	super (label);
	setText (text);
    }

    public void setText (String text) {
	this.text = text;

	textPanel.removeAll ();

	if (text != null) { 
	    int i0 = 0;
	    while (i0 < text.length ()) {
		int i1 = text.indexOf ('\n', i0);
		if (i1 == -1) i1 = text.length ();
		//System.out.println ("i0: "+i0+" i1: "+i1+ " text: "+text);
		textPanel.add (new java.awt.Label (text.substring (i0, i1)));
		i0 = i1 + 1;
	    }
	}
    }

    public String getText () {
	return text;
    }

    java.awt.Component getField () {
	return textPanel;
    }
}
