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

package org.kobjects.bibtex;

import java.io.*;

/**
 * @author haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

public class BibtexWriter {

	Writer writer;
	String newLine = System.getProperty("line.separator", "" +((char) 10));

	public BibtexWriter(Writer writer) {
		this.writer = writer;
	}

	public void startEntry(String type, String key) throws IOException {
		writer.write("@" + type + "{" + key);
	}

	public void writeField(String name, String value) throws IOException {

		writer.write(',');
		writer.write(newLine);
		writer.write("  " + name + " = ");
		
		StringBuffer buf = new StringBuffer();
		boolean enclose = false;
		
		for (int k = 0; k < value.length(); k ++) {
			char c = value.charAt(k);
			if ((c < '0' || c > '9')
				&& (c < 'a' || c > 'z')
				&& (c < 'A' || c > 'Z')) {

				enclose = true;				
				boolean escaped = false;

				for (int i = 0; i < BibtexParser.CODES.length; i += 2) {
					if (BibtexParser.CODES [i].charAt (0) == c) {
						buf.append(BibtexParser.CODES[i+1]);
						escaped = true;
						break;
					}
				}
				if (!escaped) 
					buf.append (c);
			}
			else 
				buf.append(c);
		}

		value = enclose ? "{" + buf +"}" : buf.toString();
		writer.write(value);
	}

	public void endEntry() throws IOException {
		writer.write (newLine);
		writer.write('}');
		writer.write(newLine);
		writer.write(newLine);
		writer.write(newLine);
	}

	public void close() throws IOException {
		writer.close();
	}
}
