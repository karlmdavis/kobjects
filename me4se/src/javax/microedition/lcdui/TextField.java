package javax.microedition.lcdui;

public class TextField extends Item {

    public static final int ANY = 0;
    public static final int EMAILADDR = 1;
    public static final int NUMERIC = 2;
    public static final int PHONENUMBER = 3;
    public static final int URL = 4;
    public static final int PASSWORD = 0x010000;
    public static final int CONSTRAINT_MASK = 0x0ffff;


    java.awt.TextField field;

    public TextField (String label, String text, int maxSize, int constraints) {
	super (label);
	field = new java.awt.TextField (text);
	if (maxSize < 8) field.setColumns (maxSize);
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
