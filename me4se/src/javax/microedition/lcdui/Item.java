package javax.microedition.lcdui;


public abstract class Item {

    Form form;

    class ItemLabel extends java.awt.Label {
	
	public java.awt.Dimension getMinimumSize () {
	    java.awt.Dimension d = super.getMinimumSize ();
	    if (labelText == null || labelText.length () == 0) 
		d.height = 0;
	    return d;
	}

	public java.awt.Dimension getPreferredSize () {
	    return getMinimumSize ();
	}
    }
    
    java.awt.Label label = new ItemLabel ();
    String labelText;
    
    Item () {	
    }



    Item (String lbl) {
	setLabel (lbl);
    }
    


    abstract java.awt.Component getField ();

    
    public void setLabel (String lbl) {
	if (lbl == null) 
	    label.setText ("");
	else 
	    label.setText (lbl.replace ('\n', ' '));
	
	label.invalidate ();
	labelText = lbl;
	if (form != null) form.validate ();
    }
    

    public String getLabel () {
	return labelText;
    }

}
