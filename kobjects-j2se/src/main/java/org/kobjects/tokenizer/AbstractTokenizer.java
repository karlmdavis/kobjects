package org.kobjects.tokenizer;

import java.util.Vector;

import org.kobjects.util.Strings;

/** 
 * A simple tokenizer. 
 * Manages the peek queue and the file position. */

public abstract class AbstractTokenizer {

	String expr;
	Vector next = new Vector();
	/** description or url of the source file location */
	String location;
	int pos;
	int lstart;
	int line;

	public static final String EOF = " ";

	public AbstractTokenizer(String expr) {
		if (expr == null)
			throw new NullPointerException("Cannot initialize Tokeninzer without expression");
		this.expr = expr;
	}

	public String peek() {
		return peek(0);
	}

	public boolean peek(String token){
		return peek(0).equals(token);
	}

	public void read(String token){
		require(token);
	}

	public String peek(int ofs) {
		int len = expr.length();

		while (ofs >= next.size()) {
			while (pos < len && expr.charAt(pos) <= ' ') {
				if (expr.charAt(pos) == '\n') {
					lstart = pos + 1;
					line++;
				}
				pos++;

			}

			if (pos >= len)
				next.addElement(EOF);
			else {
				String n = readImpl();
				if(n != null) next.addElement(n);
			}
		}

		return (String) next.elementAt(ofs);
	}

	protected int peekChar(int ofs) {
		return pos + ofs >= expr.length() ? -1 : expr.charAt(pos + ofs);
	}

	protected int readChar() {
		return pos >= expr.length() ? -1 : expr.charAt(pos++);
	}
	
	public void setLocation(String location){
		this.location = location;
	}

	/** Precodition: whitespace was skipped 
	 * please return null value for comments */

	protected abstract String readImpl();
	
	public void require(String token) {
		String read = read();
		if (!read.equals(token))
			throw new ParsingException(this, "expected: '" + token+ "' reading: '"+read+"'");
	}

	public String read() {
		String result = peek();
		next.removeElementAt(0);
//		System.out.println("read token: '"+result+"'");
		return result;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("\r\n");
		int end = expr.indexOf('\n', lstart);
		if (end == -1)
			end = expr.length();

		if (location != null)
			buf.append("File: " + location);

		buf.append("Line: " + line + " Column: " + (pos - lstart) + "\r\n");
		buf.append(expr.substring(lstart, end));
		buf.append("\r\n");
		buf.append(Strings.fill("", pos, ' '));
		buf.append('^');
		return buf.toString();
	}

}