package org.kobjects.xmlrpc;

import java.io.IOException;
import java.io.Reader;

import org.kobjects.xml.XmlReader;

public class XmlParser extends XmlReader
{
    public XmlParser(Reader reader) throws IOException {
        super(reader);
    }

    public int nextTag() throws IOException {
        int type = getType();
        next();
        if (type == TEXT && isWhitespace())
            next();
        if (type != END_TAG && type != START_TAG)
            exception("unexpected type");
        return type;
    }

    public String nextText() throws IOException {
        int type = getType();

        if (type != START_TAG)
            exception("precondition: START_TAG");

        next();

        String result;

        if (type == TEXT) {
            result = getText();
            next();
        }
        else
            result = "";

        if (type != END_TAG)
            exception("END_TAG expected");

        return result;
    }

}


