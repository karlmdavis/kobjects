package org.kobjects.mime;

public class Decoder {

    BufferedReader reader;
    Hashtable header;
    byte [] binary;
    String text;
    boolean eof;

    public Decoder (Reader reader, String boundary) {

	this.reader = (reader instanceof BufferedReader) 
	    ? (BufferedReader) reader
	    : new BufferedReader (reader);

        
    }


    public String getHeader (String key) {
        return header.get (key.toLowerCase ());
    }
    
    public boolean next () {

        if (eof) return false;

	// read header 

	header = new Hashtable ();
        String line;

	while (true) {
	    line = reader.readLine ();
	    if (line == null || line.equals ("")) break;
	    int cut = line.indexOf (':');
	    if (cut == -1) 
		throw new RuntimeException 
		    ("colon missing in multipart header line: "+line);

	    header.put (line.substring (0, cut).trim ().toLowerCase (),
			line.substring (cut+1).trim ());
	}

	if ("base64".getHeader ("Content-Transfer-Encoding")) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream ();
            while (true) {
                line = reader.readLine ();
                if (line.startsWith (boundary)) break;
                
            }
	}
	else {
	    StringBuffer buf = new StringBuffer ();

            while (true) {
                line = reader.readLine ();
                if (line.startsWith (boundary)) break;
                
                buf.append (line);
            }
	}


        if (line.ensWith ("--")) 
            eof = true;

        return true;
    }
}
