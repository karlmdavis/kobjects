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
                ? Field.DOUBLE 
                : Field.STRING;

            Field field = new Field (type, 
                                     orig.getName (), orig.getLabel (), 
                                     orig.getSize (), null, null);
            

            addField (field);
        }
    }


    public void refresh (Record record) {
        basis.refresh (record);
    }


    public void update (Record record) {
        throw new RuntimeException ("ReadOnly!");
    }
    
    
    public void close () {
        basis.close ();
    }

}
