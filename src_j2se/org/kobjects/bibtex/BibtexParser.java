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

	public BibtexParser(Reader reader) throws IOException {
		this.reader = reader;
		peek = reader.read();
	}

	public Hashtable nextEntry() throws IOException {

		if (reader == null)
			throw new RuntimeException("Read past end of bibtex file");

		readTo("@<");
		int i = read();
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

		//	  System.out.println ("type: " + type.toString () + " id: " + id.toString ().trim ());

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

		addProperty("type", type);
		addProperty("key", key);
	}

	void addProperty(String id, String value) {

		currentEntry.put(id.toLowerCase().trim(), value);
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
					new FileReader("/home/haustein/artikel/literatur/lit.bib")));

		while (true) {
			Hashtable t = p.nextEntry();
			if (t == null)
				break;
			System.out.println("" + t);
		}
	}

}
