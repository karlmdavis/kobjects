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
	    int i = 0;
	    int lastI = 0;
	    int k = -1;
	    while ( i < text.length() ) 
	    {
	    	char current = text.charAt( i );
	    	if( current==' ' || current=='\n' || i== ( text.length() - 1 ) )
	    		k = i;
	    	if( ( i - lastI ) == 30 || i== ( text.length() - 1 ) )
	    	{
	    		if( ( k < 0 ) || ( (i-k) > 10 ) )
					k = i;
				i = k;
				int iCut = i+1;
				if( text.charAt(i) == ' ' || text.charAt(i) == '\n' )
					iCut = i;
				textPanel.add (new java.awt.Label (text.substring ( lastI , iCut  ) ));
	    		lastI = i;
	    	}
			i++;
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
