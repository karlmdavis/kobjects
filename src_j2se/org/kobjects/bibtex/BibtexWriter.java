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
		for (int k = value.length()-1; k >= 0; k--) {
			char c = value.charAt(k);
			if ((c < '0' || c > '9')
				&& (c < 'a' || c > 'z')
				&& (c < 'A' || c > 'Z')) {
				value = "{" + value + "}";
				break;
			}
		}
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
