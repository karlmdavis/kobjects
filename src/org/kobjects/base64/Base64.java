// kObjects
//
// Copyright (C) 2001 Stefan Haustein, Oberhausen (Rhld.), Germany
//
// Contributors: 
//
// STATUS: 
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License as
// published by the Free Software Foundation; either version 2 of the
// License, or (at your option) any later version. This program is
// distributed in the hope that it will be useful, but WITHOUT ANY
// WARRANTY; without even the implied warranty of MERCHANTABILITY or
// FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
// License for more details. You should have received a copy of the
// GNU General Public License along with this program; if not, write
// to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
// Boston, MA 02111-1307, USA.

package org.kobjects.base64;

import java.io.*;


public class Base64 {

    static final String charTab = 
	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"; 


    public static String encode (byte [] data) throws IOException {

	StringBuffer buf = new StringBuffer (data.length * 3 / 2);

	int i = 0;
	int end = data.length - 3;

	while (i <= end) {
	    int d = ((((int) data [i]) & 0x0ff) << 16) 
		| ((((int) data [i+1]) & 0x0ff) << 8)
		| (((int) data [i+2]) & 0x0ff);

	    buf.append (charTab.charAt ((d >> 18) & 63));
	    buf.append (charTab.charAt ((d >> 12) & 63));
	    buf.append (charTab.charAt ((d >> 6) & 63));
	    buf.append (charTab.charAt (d & 63));

	    i += 3;

	    if (i % 45 == 0) buf.append ("\r\n");
	}

	if (i == data.length - 2) {
	    int d = ((((int) data [i]) & 0x0ff) << 16) 
		| ((((int) data [i+1]) & 255) << 8);

	    buf.append (charTab.charAt ((d >> 18) & 63));
	    buf.append (charTab.charAt ((d >> 12) & 63));
	    buf.append (charTab.charAt ((d >> 6) & 63));
	    buf.append ("=");
	}
	else if (i == data.length - 1) {
	    int d = (((int) data [i]) & 0x0ff) << 16;

	    buf.append (charTab.charAt ((d >> 18) & 63));
	    buf.append (charTab.charAt ((d >> 12) & 63));
	    buf.append ("==");
	}
	return buf.toString ();
    }

    static int decode (char c) {
	if (c >= 'A' && c <= 'Z') 
	    return ((int) c) - 65;
	else if (c >= 'a' && c <= 'z') 
	    return ((int) c) - 97 + 26;
	else if (c >= '0' && c <= '9')
	    return ((int) c) - 48 + 26 + 26;
	else switch (c) {
	case '+': return 62;
	case '/': return 63;
	case '=': return 0;
	default:
	    throw new RuntimeException ("unexpected code: "+c);
	}
    }
		


    public static byte [] decode (String s) {

	int i = 0;
	ByteArrayOutputStream bos = new ByteArrayOutputStream ();
	int len = s.length ();
	
	while (true) { 
	    while (i < len && s.charAt (i) <= ' ') i++;

	    if (i == len) break;

	    int tri = (decode (s.charAt (i)) << 18)
		+ (decode (s.charAt (i+1)) << 12)
		+ (decode (s.charAt (i+2)) << 6)
		+ (decode (s.charAt (i+3)));
	    
	    bos.write ((tri >> 16) & 255);
	    if (s.charAt (i+2) == '=') break;
	    bos.write ((tri >> 8) & 255);
	    if (s.charAt (i+3) == '=') break;
	    bos.write (tri & 255);

	    i += 4;
	}
	return bos.toByteArray ();
    }
}


