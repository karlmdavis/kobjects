package org.kobjects.xmlrpc;

import java.io.FileReader;
import org.kobjects.xml.XmlReader;

public class Driver 
{
    public static void main(String[] args) throws Exception {
        XmlReader parser = new XmlReader(new FileReader(args[0]));
        XmlRpcParser rpcParser = new XmlRpcParser(parser);
        rpcParser.parseResponse();
    }
}
