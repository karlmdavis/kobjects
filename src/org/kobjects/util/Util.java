package org.kobjects.util;

import java.io.*;

public final class Util {

	/** 
	 * Writes the contents of the input stream to the 
	 * given output stream and closes the input stream.
	 * The output stream is returned */

    public static OutputStream streamcopy(
        InputStream is,
        OutputStream os)
        throws IOException {
        byte[] buf = new byte[Runtime.getRuntime ().freeMemory () >= 1048576 
         ? 16384 : 128];
        while (true) {
            int count = is.read(buf, 0, buf.length);
            if (count == -1)
                break;
            os.write(buf, 0, count);
        }
        is.close();        
        return os;
    }

}
