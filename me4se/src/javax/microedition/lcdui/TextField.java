package javax.microedition.lcdui;

public class TextField extends Item {

    public static final int ANY = 0;
    public static final int NUMERIC = 1;

    java.awt.TextField field;

    public TextField (String label, String text, int maxSize, int constraints) {
	super (label);
	field = new java.awt.TextField (text, maxSize);
    }

    public void setString (String text) {
	field.setText (text);
    }

    public String getString () {
	return field.getText ();
    }

    java.awt.Component getField () {
	return field;
    }
}
