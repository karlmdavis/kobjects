package org.kobjects.util;

/**
 * @author haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Strings {

	public static String replace(String src, String what, String by) {
		
		int i0 = src.indexOf(what);
		
		if (i0 == -1) return src;
		
		StringBuffer buf = new StringBuffer(src.substring(0, i0));
		while(true) {
			buf.append(by);
			i0 += what.length();
			int i1 = src.indexOf(what, i0);
			if (i1 == -1) break;
			buf.append(src.substring(i0, i1));
			i0 = i1;
		}	
		
		buf.append(src.substring(i0));
		return buf.toString();
	}

	public static String toAscii(String src){
		StringBuffer buf = new StringBuffer();
		
		for(int i=0; i < src.length(); i++) {
			char c = src.charAt(i);
			switch(c){
				default: buf.append(c);
			}
		}	
		
		return buf.toString();
	}

	public static String fill(String s, int len, char c) {
	    boolean left = len < 0;
	    len = Math.abs(len);
	
	    if (s.length() >= len)
	        return s;
	
	    StringBuffer buf = new StringBuffer();
	    len -= s.length();
	    while (len > 0) {
	        buf.append(c);
	        len--;
	    }
	
	    if (left) {
	        buf.append(s);
	        return buf.toString();
	    }
	    return s + buf.toString();
	}
}
