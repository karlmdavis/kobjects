package org.kobjects.serialization;


/** provides get and set methods for properties. Can be used to
    replace reflection (to some extend) for "serialization-aware"
    classes. */



public interface SimpleSerializable {

    //public String getType (); -> subinterface

    Object getProperty (int index);

    /** returns the number of serializable properties */

    int getPropertyCount (); 


    /** sets the property with the given index to the given value. */

    void setProperty (int index, Object value);
    
}
