/*
 * Created on 24.05.2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package org.kobjects.tokenizer;

/**
 * @author haustein
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ParsingException extends RuntimeException {

		AbstractTokenizer tokenizer;
        
		public ParsingException(AbstractTokenizer tokenizer, String text) {
			super (text+"\r\n"+tokenizer.toString());
			this.tokenizer = tokenizer;
		}
    
    
		public int getLine() {
			return tokenizer.line;
		}

		public int getColumn() {
			return tokenizer.pos-tokenizer.lstart;
		}

	}

