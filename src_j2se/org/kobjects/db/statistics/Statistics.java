package org.kobjects.db.statistics;

import org.kobjects.db.*;


public class Statistics {



    public static FieldStatistics [] generate (Table table) {

        FieldStatistics stat[] = new FieldStatistics [table.getFieldCount ()];

        for (int i = 0; i < stat.length; i++) 
            stat [i] = new FieldStatistics ();

        Record r = table.select ();

        while (r.next ()) {

            for (int i = 0; i < stat.length; i++) 
                stat [i].update (r.getObject (i));
        }


        return stat;
    }


}
