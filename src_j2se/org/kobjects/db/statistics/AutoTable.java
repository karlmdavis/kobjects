package org.kobjects.db.statistics;

import org.kobjects.db.*;

/** A table based on another table, but with "enhanced" field types
    inferred from the field statistics. Performs a complete table scan
    for initialization. */

public class AutoTable extends Table {

    Table basis;
    FieldStatistics [] stats;
    
    public AutoTable (Table basis) {
        this.basis = basis;
        stats = Statistics.generate (basis);
        
        for (int i = 0; i < stats.length; i++) {
            Field orig = basis.getField (i);
            
            int type = stats [i].numeric 
                ? (stats [i].decimals == 0 
                   ? (orig.getSize () > 8 ? Field.LONG : Field.INTEGER) 
                   : Field.DOUBLE) 
                : Field.STRING;

 
            Field f = addField (orig.getName (), type);

            f.setSize (orig.getSize ());
            if (stats [i].numeric) 
                f.setConstraints (stats [i].decimals);
        }
    }


    public void init (String [] argv) {
        throw new RuntimeException ("please use constructor");
    }


    public void loadRecord (Record record) {
       basis.loadRecord (record);
    }


    public void saveRecord (Record record) {
        throw new RuntimeException ("ReadOnly!");
    }
    
    
    public void close () {
        basis.close ();
    }

}
