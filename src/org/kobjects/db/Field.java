package org.kobjects.db;



public class Field {

    public static final byte BOOLEAN = 1; // Radio true/false oder eigene Werte
    public static final byte INTEGER = 2; // Edit oder eigene Werte, dann Radio (<5) oder Liste (>=5)
    public static final byte STRING  = 4; // nix
    public static final byte BINARY  = 5; // nix
    public static final byte DOUBLE  = 6;
    public static final byte LONG  = 7;
    /*
      public static final byte SET     = 6; // wie Integer behandeln, aber andere Anzeige (CheckBoxes)
    */
    
    
    //    int id;
    int type;
    String name;
    
    String label;
    int size;
    int constraints;

    //    int constraints;
    String[] values;
    Object initial;
    

    Field (int type, String name) {
        this.type = type;
        this.name = name;
        this.label = name;
    }


    public void setLabel (String label) {
        this.label = label;
    }



    /** Overall size, including decimal digits */

    public void setSize (int size) {
        this.size = size;
    }


    /** For INT, LONG and DOUBLE fields, this is the number of decimal digits */

    public void setConstraints (int constraints) {
        this.constraints = constraints;
    }



    public void setValues  (String [] values) {
        this.values = values;
    }



    public void setInitial (Object initial) {
        this.initial = initial;
    }
    
    
    public String getName() {
        return name;
    }
    
    public int getType() {
        return type;
    }
    
    public String getLabel() {
        return label;
    }
    
    public int getSize() {
        return size;
    }
    

    public int getConstraints() {
        return constraints;
    }
    

    public String[] getValues() {
        return values;
    }
    

    public Object getInitial() {
        return initial;
    }
}


