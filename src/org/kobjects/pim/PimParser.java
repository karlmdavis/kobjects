package org.kobjects.pim;

import java.io.IOException;
import java.io.Reader;

import org.kobjects.io.LookAheadReader;

/**
 * @author Stefan Haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 */
public class PimParser {

    LookAheadReader reader;

    public PimParser(Reader reader) {
        this.reader = new LookAheadReader(reader);
    }

    public PimItem readItem() throws IOException {
        if (!readName().equals("begin"))
            throw new RuntimeException("'begin:' expected");

        PimItem item = new PimItem((String) readValue());

        while (true) {
            String name = readName();
            if (name.equals("end")) break;
            
            PimField field = new PimField(name);
            readProperties(field);
            field.setValue(readValue());
        }

        readValue();

        return item;
    }

    String readName() throws IOException {
        return reader.readTo(":;").trim().toLowerCase();
    }

    Object readValue() {
        return reader.readLine();
    }

    void readProperties(PimField field) throws IOException {
        reader.readTo(':');
    }

}
