package org.kobjects.xmlrpc;

import java.io.FileReader;

public class Driver 
{
    public static void main(String[] args) throws Exception {
        XmlParser parser = new XmlParser(new FileReader(args[0]));
        XmlRpcParser rpcParser = new XmlRpcParser(parser);
        rpcParser.parseResponse();
    }
}
