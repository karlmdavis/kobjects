package org.kobjects.pim;

import java.io.IOException;
import java.io.Reader;
import java.util.Vector;

import org.kobjects.io.LookAheadReader;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class PimParser {

    LookAheadReader reader;
	Class type;

    public PimParser(Reader reader, Class type) {
        this.reader = new LookAheadReader(reader);
        this.type = type;
    }

    public PimItem readItem() throws IOException {

    	String beg = readName();
    	if (beg == null) return null;
    	
        if (!beg.equals("begin"))
            throw new RuntimeException("'begin:' expected");

    	PimItem item;
		try {
			item = (PimItem) type.newInstance();
		}
		catch(Exception e) {
    		throw new RuntimeException(e.toString());
    	}
			
       if (!item.getType().equals(readStringValue().toLowerCase()))
       		throw new RuntimeException ("item types do not match!");

        while (true) {
            String name = readName();
            if (name.equals("end")) break;
            
            PimField field = new PimField(name);
            readProperties(field);
            Object value;
            switch (item.getType(name)) {
            	case PimItem.TYPE_STRING_ARRAY: 
            		value = readArrayValue(); 
            		break;
            	default: 
            		value = readStringValue();
            }
            field.setValue(value);
        	System.out.println("value:"+value);
            item.addField(field);
        }

        readStringValue();

        return item;
    }

    String readName() throws IOException {
    	String name = reader.readTo(":;").trim().toLowerCase();
		System.out.println("name:"+name);
        return reader.peek(0) == -1 ? null : name;
    }

	String[] readArrayValue() throws IOException {
		Vector values = new Vector();
		reader.read(); // :
		
		StringBuffer buf = new StringBuffer();
		boolean stay = true;
		do {
			buf.append(reader.readTo(";\n\r"));
			switch(reader.read()) {
			case ';': values.addElement(buf.toString());
					 buf.setLength(0);
				  	 break;
			case '\r': if (reader.peek(0)=='\n') reader.read();
			case '\n': if (reader.peek(0) > ' ') stay = false; 
		}
		}
		while(stay);
		
		if(buf.length() != 0) 
			values.addElement(buf.toString());
		
		String[] ret = new String[values.size()];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = (String) values.elementAt(i);
		}
		return ret;
	}


    String readStringValue() throws IOException  {
    	reader.read();
        String value = reader.readLine();
        while (reader.peek(0) < 33 && reader.peek(0) != -1){ 
        	reader.read();
        	value = value + reader.readLine();
    	}
    	return value;
    }

    void readProperties(PimField field) throws IOException {
        reader.readTo(':');
    }

}
