package org.kobjects.db.statistics;

import java.util.Hashtable;

public class FieldStatistics {


    Hashtable values = new Hashtable ();
    double min = Double.POSITIVE_INFINITY;
    double max = Double.NEGATIVE_INFINITY;

    int count;
    int nullCount;
    boolean numeric = true;
    
    
    public void update (Object v) {
        count++;

        if (v == null) 
            nullCount++;
        else {
            String value = v.toString ();
            Integer count = (Integer) values.get (value);
            if (count == null) count = new Integer (1);
            else count = new Integer (count.intValue () + 1);
            values.put (value, count);

            if (numeric) {
                value = value.trim ();
                if (value.equals ("")) 
                    nullCount++;
                else {
                    try {
                        double d = Double.parseDouble (value);
                        if (d > max) max = d;
                        if (d < min) min = d;
                    }
                    catch (NumberFormatException e) {
                        numeric = false;
                    }
                }
            }
        }
    }



    public String toString () {
        return "count: "+count + " nullCount: "+ nullCount 
            + " distinct values: "+(values.size() < 10 ? ""+values : ""+values.size ()) + "numeric: " 
            + (numeric ? ("yes;  min: "+ min + " max: "+ max) : "no");
    }

}
