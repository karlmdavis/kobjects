package org.kobjects.mime;

public class Decoder {

    BufferedReader reader;
    Hashtable header;
    byte [] binary;
    String text;

    public Decoder (Reader reader, String boundary) {

	this.reader = (reader instanceof BufferedReader) 
	    ? (BufferedReader) reader
	    : new BufferedReader (reader);
    }

    
    public boolean next () {

	// read header 

	header = new Hashtable ();

	while (true) {
	    String line = reader.readLine ();
	    if (line == null || line.equals ("")) break;
	    int cut = line.indexOf (':');
	    if (cut == -1) 
		throw new RuntimeException 
		    ("colon missing in multipart header line: "+line);

	    header.put (line.substring (0, cut).trim ().toLowerCase (),
			line.substring (cut+1).trim ());
	}

	if ("base64".equals (header.get ("Content-Transfer-Encoding"))) {

	}
	else {
	    StringBuffer buf = new StringBuffe ();
	}
    }
}
