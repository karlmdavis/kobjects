package org.kobjects.io;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Stefan Haustein
 *
 * Similar to PushbackReader, but the other way around: it is possible to "peek"
 * ahead in the stream without consuming characters. Performs buffering because
 * a buffer is needed anyway.
 */


public class LookAheadReader extends Reader {


    char [] buf = new char[Runtime.getRuntime().freeMemory() > 1000000 ? 16384 : 128];

    int bufPos = 0;
    int bufValid = 0;
    Reader reader;


    public LookAheadReader(Reader r) {
        this.reader = r;
    }

    /**
     * @see java.io.Reader#read(char[], int, int)
     */
    public int read(char[] cbuf, int off, int len) throws IOException {
        
        if (bufValid == 0) {
            if (peek(0) == -1) return -1;
        }
        
        if (len > bufValid) len = bufValid;
        if (len > buf.length - bufPos) len = buf.length - bufPos;
        
        System.arraycopy (buf, bufPos, cbuf, off, len);
        
        bufValid -= len;
        bufPos = bufPos + len;
        if (bufPos > buf.length) bufPos -= buf.length;
        
        return len;
    }
    
    public String readTo(String chars) throws IOException {

        StringBuffer buf = new StringBuffer();

        while (peek(0) != -1 && chars.indexOf((char) peek(0)) == -1) {
            buf.append((char) read());
        }

        return buf.toString();
    }

    public String readTo(char c) throws IOException {
        StringBuffer buf = new StringBuffer();

        while (peek(0) != -1 && peek(0) != c) {
            buf.append((char) read());
        }

        return buf.toString();
    }
    
    
    /**
     * @see java.io.Reader#close()
     */
    public void close() throws IOException {
        reader.close();
    }


//    public String readLine() {
//    }


    public int read() throws IOException {
        int result = peek(0);            
        
        if (result != -1) {          
            if (++bufPos == buf.length) bufPos = 0;
            bufValid--;
        }
        
        return result;         
    }

    public int peek(int delta) throws IOException{

        if (delta > 127)
            throw new RuntimeException ("peek > 127 not supported!");

        while (delta >= bufValid) {
            int startPos = (bufPos + bufValid) % buf.length;
            int count = Math.max(buf.length - bufPos, buf.length - bufValid);
            
            count = reader.read(buf, startPos, count);
            
            if (count == -1) return -1;
            
            bufValid += count;
        }

        return buf[bufPos + delta % buf.length];
    }
    /**
     * Method readLine.
     * @return Object
     */
    public String readLine() {
        throw new RuntimeException("NYI");
    }

}
