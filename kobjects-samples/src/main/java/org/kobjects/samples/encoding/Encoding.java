/* Copyright (c) 2002,2003, Stefan Haustein, Oberhausen, Rhld., Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The  above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. */

package org.kobjects.samples.encoding;

import java.util.*;
import org.kobjects.isodate.*;


public class Encoding {



    public static void main (String [] argv) {
	
	Date date = new Date ();

	String id = IsoDate.dateToString (date, IsoDate.DATE_TIME);
	System.out.println ("ISO Date Now:    "+id);
	Date date2 = IsoDate.stringToDate (id, IsoDate.DATE_TIME);
	String id2 = IsoDate.dateToString (date2, IsoDate.DATE_TIME);
	System.out.println ("After Roundtrip: "+id2);
	
	System.out.println ("Equals:          "+date.equals (date2));

    }



}
