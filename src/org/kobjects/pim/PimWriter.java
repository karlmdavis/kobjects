package org.kobjects.pim;

import java.io.*;
import java.util.Enumeration;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class PimWriter {
    Writer writer;

    public PimWriter(Writer writer){
        this.writer = writer;
    }


    public void writeEntry (PimItem item) throws IOException {
        writer.write("begin:");
        writer.write(item.getType());
        writer.write("\r\n");
        
        for(Enumeration e = item.fieldNames(); e.hasMoreElements();) {
            String name = (String) e.nextElement();
            for (int i = 0; i < item.getFieldCount(name); i++) {
                PimField field = item.getField(name, i);
                writer.write(name);
                writer.write(':');
                writer.write(field.getValue().toString());
                writer.write("\r\n");            
            }
        }

        writer.write("end:");
        writer.write(item.getType());
        writer.write("\r\n\r\n");
        
    }

}
