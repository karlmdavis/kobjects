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
import java.util.*;

/** 
 * Reads entries from a bibtex stream and returns them as separate hashtables
 * known special chars such as german umlauts are converted to unicode */

public class BibtexParser {

	Reader reader;
	int peek;

	//    Hashtable indices = new Hashtable();
	//    Vector entries = new Vector();
	Hashtable currentEntry;

	static String [] CODES = {
		"#", "\\#",
		"$", "\\$", 
		"&", "\\&",
		"_", "\\_",
		"^", "\\^",
		"%", "\\%",

//		"\u00a7", "\\S ",
//		"\u00a9", "\\copyright ", 
//		"\u00b6", "\\P ",

		"\u00c0", "\\`{A}",
		"\u00c1", "\\'{A}",
		"\u00c2", "\\^{A}",
		"\u00c3", "\\~{A}",
		"\u00c4", "\\\"{A}",
		"\u00c4", "{\\\"A}",
		"\u00c5", "{\\AA}",
		"\u00c6", "{\\AE}",
		"\u00c7", "\\c{C}",
		"\u00c8", "\\`{E}",
		"\u00c9", "\\'{E}",
		"\u00ca", "\\^{E}",
		"\u00cb", "\\\"{E}",
		"\u00cc", "\\`{I}",
		"\u00cd", "\\'{I}",
		"\u00ce", "\\^{I}",
		"\u00cf", "\\\"{I}",
		
		"\u00d1", "\\~{N}",
		"\u00d2", "\\`{O}",
		"\u00d3", "\\'{O}",
		"\u00d4", "\\^{O}",
		"\u00d5", "\\~{O}",
		"\u00d6", "\\\"{O}",
		"\u00d6", "{\\\"O}",
		"\u00d8", "{\\O}",
		"\u00d9", "\\`{U}",
		"\u00da", "\\'{U}",
		"\u00db", "\\^{U}",
		"\u00dc", "\\\"{U}",		
		"\u00dc", "{\\\"U}",		
		"\u00dd", "\\'{Y}",
		"\u00df", "{\\ss}",
		
		"\u00e0", "\\`{a}",
		"\u00e1", "\\'{a}",
		"\u00e2", "\\^{a}",
		"\u00e3", "\\~{a}",
		"\u00e4", "\\\"{a}",
		"\u00e4", "{\\\"a}",
		"\u00e5", "{\\aa}",
		"\u00e6", "{\\ae}",
		"\u00e7", "\\c{c}",
		"\u00e8", "\\`{e}",
		"\u00e9", "\\'{e}",
		"\u00ea", "\\^{e}",
		"\u00eb", "\\\"{e}",
		"\u00ec", "\\`{\\i}",
		"\u00ed", "\\'{\\i}",
		"\u00ee", "\\^{\\i}",
		"\u00ef", "\\\"{\\i}",

		"\u00f1", "\\~{n}",
		"\u00f2", "\\`{o}",
		"\u00f3", "\\'{o}",
		"\u00f4", "\\^{o}",
		"\u00f5", "\\~{o}",
		"\u00f6", "\\\"{o}",
		"\u00f6", "{\\\"o}",
		
		"\u00f8", "{\\o}",
		"\u00f9", "\\`{u}",
		"\u00fa", "\\'{u}",
		"\u00fb", "\\^{u}",
		"\u00fc", "\\\"{u}",				
		"\u00fc", "{\\\"u}",				
		"\u00fd", "\\'{y}",
		"\u00ff", "\\\"{y}"};
		

	public BibtexParser(Reader reader) throws IOException {
		this.reader = reader;
		peek = reader.read();
	}


	static String replace (String src, String replace, String by) {
		
		int i = src.indexOf (replace);
		return (i == -1) 
			? src
			: (src.substring (0, i) + by + replace (src.substring (i + replace.length()), replace, by));		
	}


	public static String toUnicode(String s) {

		if (s.indexOf ('\\') != -1) {
			for (int i = 0; i < CODES.length; i+= 2) {
				s = replace(s, CODES [i+1], CODES[i]); 	
			}
		}
		return s;
	}


	public Hashtable nextEntry() throws IOException {

		if (reader == null)
			throw new RuntimeException("Read past end of bibtex file");

		readTo("@<*");
		int i = read();
		
		if (i == '*') {
			Hashtable del = new Hashtable();
			del.put ("bibkey", "*" + readTo("\n\r\t "));
			return del;
		}
		
		if (i != '@') {
			reader.close();
			reader = null;
			currentEntry = null;
			return null;
		}

		currentEntry = new Hashtable();

		// mit '<' wird <ende> vom emacs erkannt...
		//if (peek == -1) break; redundant with i != '@'

		StringBuffer type = new StringBuffer();
		StringBuffer id = new StringBuffer();

		type.append(readTo('{'));

		read();

		id.append(readTo(",}"));
		int c = read();

		//	  System.out.println ("type: " + type.toString () + " id: " + id.toString 
		// ().trim ());

		// "eintrag"

		addEntry(type.toString().trim().toLowerCase(), id.toString().trim());

		if (c == ',') {
			while (readLine()) {
				// System.out.println ("rl");
			}
		}

		//      System.out.println ("c");

		return currentEntry;
	}

	String readTo(String chars) throws IOException {

		StringBuffer buf = new StringBuffer();

		while (peek != -1 && chars.indexOf((char) peek) == -1) {
			buf.append((char) peek);
			peek = reader.read();
		}

		return buf.toString();
	}

	String readTo(char c) throws IOException {
		StringBuffer buf = new StringBuffer();

		while (peek != -1 && peek != c) {
			buf.append((char) peek);
			peek = reader.read();
		}

		return buf.toString();
	}

	int read() throws IOException {
		int result = peek;
		peek = reader.read();
		return result;
	}

	void addEntry(String type, String key) {
		currentEntry = new Hashtable();

		addProperty("bibtype", type);
		addProperty("bibkey", key);
	}

	void addProperty(String id, String value) {

		currentEntry.put(id.toLowerCase().trim(), toUnicode(value));
	}

	void recurse(StringBuffer buf) throws IOException {

		while (true) {
			buf.append(readTo("{}"));

			if (read() != '{') {
				break;
			}

			buf.append('{');
			recurse(buf);
			buf.append('}');
		}
	}

	boolean readLine() throws IOException {
		StringBuffer idBuf = new StringBuffer();
		StringBuffer valueBuf = new StringBuffer();

		String id = readTo("}=").trim().toLowerCase();

		if (read() == '}') {
			return false;
		}

		valueBuf.append(readTo("{,}\""));

		int c = read();

		if (c == '{') {
			recurse(valueBuf);
			valueBuf.append(readTo(",}"));

			c = read();
		} else if (c == '"') {
			while (true) {
				valueBuf.append(readTo("\"\\"));
				int d = read();

				if (d == '"' || d == -1)
					break;

				valueBuf.append((char) d);
				valueBuf.append((char) read());
			}

			readTo(",}");

			c = read();
		}

		String value = valueBuf.toString().trim();

		addProperty(id, value);

		return (c == ',');
	}


	public static void main(String[] argv) throws IOException {

		BibtexParser p =
			new BibtexParser(
				new BufferedReader(
					new FileReader("/app/unido-i08/share/bibserver/database/literatur.bib")));

		while (true) {
			Hashtable t = p.nextEntry();
			if (t == null)
				break;
			System.out.println("" + t);
		}
	}

}

