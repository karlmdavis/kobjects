package org.kobjects.db.jdbc;

import org.kobjects.db.*;
import org.kobjects.util.*;
import java.sql.*;


public class JdbcTable extends Table {
    
    String name;
    boolean existing = false;
    Connection connection;
    PreparedStatement insertStatement;
    
    public JdbcTable (Connection connection, String name) {
        
        this.name = name;
        this.connection = connection;

        try {
            Statement statement = connection.createStatement ();
            //statement.executeUpdate ("DELETE FROM "+tableName);

            ResultSet dummy;
            try {
                dummy = statement.executeQuery ("SELECT * FROM "+name);
            }
            catch (SQLException e) {
                // ok, assume db does not exist...
                return;
            }
        

            ResultSetMetaData meta = dummy.getMetaData ();
            
            for (int i = 1; i <= meta.getColumnCount (); i++) {
                // meta.getColumnName (i),
                int type;
                switch (meta.getColumnType (i)) {
                case Types.VARCHAR: type = Field.STRING; break;
                case Types.REAL: type = Field.DOUBLE; break;
                default: throw new RuntimeException ("unsupported field type: "+meta.getColumnType (i));
                }
            }
            statement.close ();
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e);
        }
        
        existing = true;
    }
    

    
    public void refresh (Record record) {
        throw new RuntimeException ("NYI");
    }


    public void drop () {
        if (!existing) return;

        try {
            Statement statement = connection.createStatement ();
            statement.execute ("DROP TABLE "+name); 
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e);
        }
    }
    
    
    void create () {
        
        // build 
        StringBuffer buf = new StringBuffer ("CREATE TABLE ");
        buf.append (name);
        
        for (int i = 0; i < getFieldCount (); i++) {
            Field f = getField (i);
            buf.append (i == 0 ? " (" : ", ");
            buf.append (f.getName ());
            switch (f.getType ()) {
            case Field.DOUBLE:
                buf.append ("NUMBER ("+f.getSize () + ", 2)");
                break;
            case Field.INTEGER:
            case Field.LONGINT:
                buf.append ("NUMBER ("+f.getSize () + ")");
                break;
            case Field.STRING:
                buf.append ("VARCHAR ("+f.getSize () + ")");
                break;

            default:
                throw new RuntimeException ("Unsupported type: "+f.getType ());
            }
        }

        buf.append (")");

        try {
            Statement statement = connection.createStatement ();
            statement.execute (buf.toString ());
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e);
        }
    }
    
    /*
      CREATE TABLE MO_PARROLID ( 
      VVID            NUMBER (16)   NOT NULL, 
      PRTYP           NUMBER (5)    NOT NULL, 
      PRTYPNR         NUMBER (5)    NOT NULL, 
      LASTVERSNR      NUMBER (5)    NOT NULL, 
      LASTROL_BEGINN  NUMBER, 
      LASTROL_ENDE    NUMBER, 
      LASTPTID        NUMBER (16)   NOT NULL, 
      PRIMARY KEY ( VVID, PRTYP, PRTYPNR ) ) ; 
    */

    

    public void update (Record record) {
        
        if (record.getId () != -1) throw new RuntimeException ("update NYI");

        
        

    }


    public void close () {
    }

}
