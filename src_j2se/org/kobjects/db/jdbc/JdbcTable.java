package org.kobjects.db.jdbc;

import org.kobjects.db.*;
import org.kobjects.util.*;
import java.sql.*;
import java.util.*;

public class JdbcTable extends Table {
    
    String name;
    boolean existing = false;
    Connection connection;
    PreparedStatement insertStatement;
    

    public JdbcTable () {
    }

    public JdbcTable (Connection connection, String name) {
        this.connection = connection;
        this.name = name;

        open ();
    }


    public void init (String [] params) {

        if (params.length != 4) 
            throw new RuntimeException ("params expected: url user pw tablename");

        try {

        
            connection = DriverManager.getConnection 
                (params [0], params [1], params [2]);
            
            name = name;

            open ();
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e);
        }
    }


    private void open () {
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
                case Types.INTEGER: type = Field.INTEGER; break;
                case Types.NUMERIC: 
                case Types.REAL: type = Field.DOUBLE; break;
                default: throw new RuntimeException 
                             ("unsupported field type: "+meta.getColumnType (i));
                }
                addField (meta.getColumnName (i), type);
            }
            statement.close ();
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e);
        }
        
        existing = true;
    }
    

    
    public void loadRecord (Record record) {
        throw new RuntimeException ("NYI");
    }


    public void drop () {
        if (!existing) return;
        
        try {
            Statement statement = connection.createStatement ();
            statement.execute ("DROP TABLE "+name); 
            existing = false;
            fields = new Vector ();
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
            buf.append (' ');
            switch (f.getType ()) {
            case Field.DOUBLE:
            case Field.INTEGER:
            case Field.LONG:
                if (f.getConstraints () == 0) 
                    buf.append ("NUMBER ("+f.getSize () + ")");
                else 
                    buf.append 
                        ("NUMBER ("+f.getSize () + ", "+f.getConstraints ()+")");
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
            existing = true;
        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e, "failed: "+buf);
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

    

    public void saveRecord (Record record) {
        
        if (record.getId () != -1) throw new RuntimeException ("update NYI");

        if (!existing) create ();

        try {

            if (insertStatement == null) {
                StringBuffer buf = new StringBuffer ("INSERT INTO ");
                buf.append (name);
                for (int i = 0; i < getFieldCount (); i++) {
                    buf.append (i == 0 ? " (" : ", ");
                    buf.append (getField (i).getName ());
                }

                buf.append (") VALUES ");

                for (int i = 0; i < getFieldCount (); i++) { 
                     buf.append (i == 0 ? " (?" : ", ?");
                     // buf.append (i == 0 ? "( " : ", ");
                     // buf.append (""+record.getObject (i));
                } 
                
                
                buf.append (")");
                insertStatement = connection.prepareStatement (buf.toString ());
            }

            // connection.createStatement ().executeUpdate (buf.toString ());

           

            for (int i = 0; i < getFieldCount (); i++) 
                insertStatement.setObject (i+1, record.getObject (i));

            insertStatement.executeUpdate ();

        }
        catch (SQLException e) {
            throw new ChainedRuntimeException (e); //, "failed: "+buf.toString () );
        }


        //ins

    }


    public void close () {
    }

}
