package org.kobjects.db.text;

import java.io.*;
import org.kobjects.db.*;
import org.kobjects.db.statistics.*;
import org.kobjects.util.*;

public class SpkTable extends TextFixed {

    String tableName;

    public SpkTable (String spkFileName) {
        
        super (spkFileName.substring (0, spkFileName.length () - 4)
               + (spkFileName.endsWith (".SPK") ? ".DAT" : ".dat"));

        try {

            BufferedReader reader = new BufferedReader 
                (new FileReader (spkFileName));
            
            
            TextFixed helpTable = new TextFixed (fileName);
            
            while (true) { 
                String line = reader.readLine ();
                if (line == null) break;
                
                int cut = line.indexOf ('=');
                if (cut == -1) continue;
                String key = line.substring (0, cut).trim ();
                String value = line.substring (cut+1).trim ();
                
                if ("TABLENAME".equals (key)) {
                    tableName = value;
                }
                if (key.startsWith ("COL")) {
                    int c0 = value.indexOf (',');
                    int c1 = value.indexOf (',', c0+1);
                    int c2 = value.indexOf (',', c1+1);
                    
                    String name = value.substring (0, c0);
                    int length = Integer.parseInt (value.substring (c2+1).trim ()); 
                    
                    helpTable.addField 
                        (new Field 
                         (Field.STRING, name, name, length, null, null));
                    
                    /*
                      ColumnDef c = new ColumnDef ();
                      c.name = value.substring (0, c0);
                      c.start = Integer.parseInt (value.substring (c1+1, c2).trim ());
                      c.length = 
                      c.type = ((Integer) types.get (c.name)).intValue ();
                      base = base + (columnDefs.size () == 0 ? "( " : ", ") + c.name; 
                      columnDefs.add (c);
                    */
                }
            }
            
            /*            FieldStatistics [] fs = Statistics.generate (helpTable);

            for (int i = 0; i < fs.length; i++) {
                System.out.println (fs[i].toString ());
                }*/

        }
        catch (IOException e) {
            throw new ChainedRuntimeException (e);
        }
    }


    /** test only (!!!) 

    public static void main (String [] argv) {

        new SpkTable (argv [0]);
    }

*/
}
