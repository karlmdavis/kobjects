/*
 * Gauge.java
 *
 * Created on 2. August 2001, 09:48
 */

package javax.microedition.lcdui;

/**
 *Uses java.awt.Scrollbar as Gauge. 
 *Doesn't care of interactive and non-interactive state.
 *
 * @author  svastag
 * @version 
 */
public class Gauge extends javax.microedition.lcdui.Item {

    //java.lang.String label;
    boolean interactive;
    int value;
    
    java.awt.Scrollbar scrollbar;
    
    /** Creates new Gauge */
    public Gauge(java.lang.String label, boolean interactive, int maxValue, int initialValue) 
    throws IllegalArgumentException {
        //this.label = label;
        super(label);
        this.interactive = interactive;
        value = initialValue;
        scrollbar = new java.awt.Scrollbar(java.awt.Scrollbar.HORIZONTAL,initialValue,java.awt.Scrollbar.HORIZONTAL,0,maxValue);
    }

    public java.awt.Component getField () {
        return scrollbar;
    }
    
    /**
     *Gets the maximum value of this Gauge object. 
     */ 
    public int getMaxValue() {
        return scrollbar.getMaximum();
    }
    
    /**
     *Gets the current value of this Gauge object. 
     */
    public int getValue() {
        return scrollbar.getValue();
    }
    
    /**
     *Tells whether the user is allowed to change the value of the Gauge. 
     */
    public boolean isInteractive()  {
        return interactive;
    }
    
    /**
     *Sets the maximum value of this Gauge object. 
     */
    public void setMaxValue(int maxValue) {
        scrollbar.setMaximum(maxValue);
    }
    
    /**
     *Sets the current value of this Gauge object. 
     */
    public void setValue(int value) {
        scrollbar.setValue(value);
    }
      

    
}
