package org.kobjects.tokenizer;

import java.util.Vector;

import org.kobjects.util.Strings;

/** 
 * A simple tokenizer. 
 * Manages the peek queue and the file position. */

public class Tokenizer {

	String expr;
	Vector next = new Vector();
	/** description or url of the source file location */
	String location;
	int pos;
	int lstart;
	int line;

	public static final String EOF = " ";

	public Tokenizer(String expr) {
		if (expr == null)
			throw new NullPointerException("Cannot initialize Tokeninzer without expression");
		this.expr = expr;
	}

	public String peek() {
		return peek(0);
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
				next.addElement(readImpl());
			}
		}

		return (String) next.elementAt(ofs);
	}

	protected int peekChar(int ofs) {
		return pos + ofs >= expr.length() ? -1 : expr.charAt(pos + ofs);
	}

	protected int readChar() {
		return peekChar(pos++);
	}

	/** Precodition: whitespace was skipped */

	protected String readImpl() {
		int len = expr.length();

		char c = expr.charAt(pos++);
		StringBuffer buf = new StringBuffer();

		if ("<>-:".indexOf(c) != -1) {
			buf.append(c);
			if (pos < len && ":=>".indexOf(expr.charAt(pos)) != -1)
				buf.append(expr.charAt(pos++));
		}
		else if ("+*/=(){}[]|,;".indexOf(c) != -1)
			buf.append(c);

		else if (c == '\'' || c == '"') {
			buf.append(c);
			while (pos < len && expr.charAt(pos) != c) {
				char d = expr.charAt(pos++);
				if (d == '\\') {
					d = expr.charAt(pos++);
					switch (d) {
						case 't' :
							d = '\t';
							break;
						case 'n' :
							d = '\n';
							break;
						case 'r' :
							d = '\r';
							break;
						case '\\' :
						case '\'' :
							break;
						default :
							throw new TokenizerException(this, "Illegal escape sequence: \\" + d);
					}
				}
				buf.append(d);
			}
			pos++;
		}
		else if ((c >= '0' && c <= '9') || c == '.') {
			if (c == '.' && pos < len && (expr.charAt(pos) < '0' || expr.charAt(pos) > '9'))
				buf.append('.');
			else {
				buf.append('0');
				buf.append(c);

				while (pos < len) {
					c = expr.charAt(pos);
					if ((c < '0' || c > '9') && (c != '.') && (c != 'e') && c != 'E')
						break;
					buf.append(c);
					pos++;
				}
			}
		}

		else if (
			(c >= 'a' && c <= 'z')
				|| (c >= 'A' && c <= 'Z')
				|| (c == '_')
				|| (c == '#')
				|| c > 127) {

			buf.append(c);
			while (pos < len) {
				c = expr.charAt(pos);
				if ((c < 'a' || c > 'z')
					&& (c < 'A' || c > 'Z')
					&& (c < '0' || c > '9')
					&& (c != '_')
					&& (c <= 127))
					break;
				buf.append(c);
				pos++;
			}
		}
		else
			throw new TokenizerException(this, "Illegal char: " + c);

		return buf.toString();
	}

	public void require(String token) {
		if (!read().equals(token))
			throw new TokenizerException(this, "expected: " + token);
	}

	public String read() {
		String result = peek();
		next.removeElementAt(0);
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