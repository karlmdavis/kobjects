package org.kobjects.util;

import java.io.*;

public final class Util {

	/** 
	 * Writes the contents of the input stream to the 
	 * given output stream and closed the input stream.
	 * The output stream is returned */

    public static OutputStream streamcopy(
        InputStream is,
        OutputStream os)
        throws IOException {
        byte[] buf = new byte[128];
        while (true) {
            int count = is.read(buf, 0, 128);
            if (count == -1)
                break;
            os.write(buf, 0, count);
        }
        
        return os;
    }

}
