package javax.microedition.io;

import org.kobjects.me4se.impl.*;

public class Connector {


    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final int READ_WRITE = READ | WRITE;


    public static Connection open (String name) {
	return open (name, READ_WRITE, false);
    }

    public static Connection open (String name, int mode) {
	return open (name, mode, false);
    }


    public static Connection open (String url, int mode, boolean timeOuts) {

	int cut = url.indexOf (':');
	if (cut == -1) throw new RuntimeException (": not found!");

	String protocol = url.substring (0, cut).toLowerCase ();

	try {
	    ConnectionImpl connection = (ConnectionImpl)
		Class.forName ("org.kobjects.me4se.impl.ConnectionImpl_"+protocol).newInstance ();
	    
	    connection.open (url, mode, timeOuts);
	    return connection;
	}
	catch (Exception e) {
	    throw new RuntimeException (e.toString ());
	}

    }

}


    
