package org.kobjects.base64;

import java.io.*;


public class Base64 {

    static final String charTab = 
	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; 


    public static void encode (byte [] data, Writer w) throws IOException {

	int i = 0;
	int end = data.length - 3;

	while (i <= end) {
	    int d = ((((int) data [i]) & 0x0ff) << 16) 
		| ((((int) data [i+1]) & 0x0ff) << 8)
		| (((int) data [i+2]) & 0x0ff);

	    w.write (charTab.charAt ((d >> 18) & 63));
	    w.write (charTab.charAt ((d >> 12) & 63));
	    w.write (charTab.charAt ((d >> 6) & 63));
	    w.write (charTab.charAt (d & 63));

	    i += 3;

	    if (i % 45 == 0) w.write ("\r\n");
	}

	if (i == data.length - 2) {
	    int d = ((((int) data [i]) & 0x0ff) << 16) 
		| ((((int) data [i+1]) & 255) << 8);

	    w.write (charTab.charAt ((d >> 18) & 63));
	    w.write (charTab.charAt ((d >> 12) & 63));
	    w.write (charTab.charAt ((d >> 6) & 63));
	    w.write ("=");
	}
	else if (i == data.length - 1) {
	    int d = (((int) data [i]) & 0x0ff) << 16;

	    w.write (charTab.charAt ((d >> 18) & 63));
	    w.write (charTab.charAt ((d >> 12) & 63));
	    w.write ("==");
	}
    }
}
