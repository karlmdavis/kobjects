package org.kobjects.pim;

import java.util.*;



/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */


public class PimItem {

    Hashtable fields = new Hashtable();
    String type;

    public PimItem(String type){
        this.type = type;
        
    }
    
    public void addField(PimField field) {
        Vector v = (Vector) fields.get(field.name);

        if (v == null) {
            v = new Vector();
            fields.put(field.name, v);
        }
        
        v.addElement(field);        
    }

    public PimField getField(String name, int index) {
        return (PimField) ((Vector) fields.get(name)).elementAt(index);
    }
    
    
    public int getFieldCount(String name) {
        Vector v = (Vector) fields.get(name);
        return v == null ? 0 : v.size();
    }

}
