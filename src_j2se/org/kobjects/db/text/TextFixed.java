package org.kobjects.db.text;


import org.kobjects.db.*;
import java.io.*;

public class TextFixed extends Table {
    
    String fileName;
    int current;
    BufferedReader reader;
    int recordCount;


    public TextFixed () {
    }


    public TextFixed (String fileName) {
        this.fileName = fileName;
    }


    public void init (String [] param) {
        if (param.length != 1) 
            throw new IllegalArgumentException ("param count must be 1");

        this.fileName = param [0];
    }


    protected void loadRecord (Record record) {

        String line;

        try {
            
            if (current >= record.getId () || reader == null) {
                reader = new BufferedReader (new FileReader (fileName));
                current = 1;
            }
            

            do {
                line = reader.readLine ();
                current++;
                if (line == null) throw new IllegalArgumentException ();
            } 
            while (current < record.getId ());
        }
        catch (IOException e) {
            throw new RuntimeException (e.toString ());
        }
            
        int start = 0;
        for (int i = 0; i < getFieldCount (); i++) {
            Field f = getField (i);
            
            record.setObject (i, line.substring (start, start + f.getSize ()));
            start += f.getSize ();
        }
    } 
        

    public void saveRecord (Record record) {
        throw new RuntimeException ("Readonly!");
    }
    

    public void close () {
        if (reader != null) {
            try {
                reader.close ();
            }
            catch (IOException e) {
                throw new RuntimeException (e.toString ());
            }        
        }
    }
}
