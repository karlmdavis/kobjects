package org.kobjects.pim;

import java.util.*;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */

public class PimField {

    String name;
    Object value;
    Hashtable attributes;
    Hashtable properties;

    
    public PimField (String name) {    
        this.name = name;
    }


    public void setProperty(String name, String value) {
        if (properties == null) { 
            if (value == null) return;
            properties = new Hashtable();
        }

        if (value == null)
            properties.remove(name);
        else 
            properties.put(name, value);
    }
    /**
     * Method setValue.
     * @param object
     */
    public void setValue(Object object) {
        value = object;
    }

}
