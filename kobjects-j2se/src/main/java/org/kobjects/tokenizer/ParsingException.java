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
        
        public ParsingException(AbstractTokenizer tokenizer, String text, Exception e){
			super (text+"\r\n"+tokenizer.toString(), e);
			this.tokenizer = tokenizer;
        }
        
		public ParsingException(AbstractTokenizer tokenizer, Exception e) {
				this(tokenizer, e.getClass() == RuntimeException.class ? e.getMessage() : e.toString(), e);
			}
        
		public ParsingException(AbstractTokenizer tokenizer, String text) {
			this(tokenizer, text, null);
		}
    
    
		public int getLine() {
			return tokenizer.line;
		}

		public int getColumn() {
			return tokenizer.pos-tokenizer.lstart;
		}

	}

