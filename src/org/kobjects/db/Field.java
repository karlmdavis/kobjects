package org.kobjects.db;



public class Field {

    public static final byte BOOLEAN = 1; // Radio true/false oder eigene Werte
    public static final byte INTEGER = 2; // Edit oder eigene Werte, dann Radio (<5) oder Liste (>=5)
    public static final byte STRING  = 4; // nix
    public static final byte BINARY  = 5; // nix
    public static final byte DOUBLE  = 6;
    public static final byte LONGINT = 7;
    /*
      public static final byte SET     = 6; // wie Integer behandeln, aber andere Anzeige (CheckBoxes)
    */
    
    
    int id;
    int type;
    String name;
    
    String label;
    int maxSize;
    //    int constraints;
    String[] values;
    Object initial;
    
    public Field (int type, String name, String label, int maxSize, 
           //int constraints, 
           String[] values, Object initial) {

        this.type = type;
        this.name = name;
        this.label = label;
        this.maxSize = maxSize;
        //        this.constraints = constraints;
        this.values = values;
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
        return maxSize;
    }
    
    /*    public int getConstraints() {
        return constraints;
        }*/
    
    public String[] getValues() {
        return values;
    }
    
    public Object getInitial() {
        return initial;
    }
}


