/*
 * JAD_Parser.java
 *
 * Created on 6. August 2001, 09:55
 */

package javax.microedition.midlet;

import java.util.*;
import java.io.*;

/**
 *
 * @author  svastag
 * @version 
 */
public class JAD_Parser extends java.lang.Object {

    private Hashtable jadcontents = new Hashtable();
    
    /** Creates new JAD_Parser */
    public JAD_Parser(String jadname) {
        split(getLines(jadname));
        splitMidlets();
    }
    
    
    public String getAppProperty(String property) {
        if (jadcontents.containsKey(property)) 
            return (String) jadcontents.get(property);
        else 
            return null;
    }
    
    /**
     *@return an Array of Arrays (String) containing [0] Name, [1] Icon, [2] Class
     */
    public Object[] getMIDletList() {
        return splitMidlets();
    }
    
    /**
     *Reads the JAD-File
     */
    private Vector getLines(String jadname) {
        Vector unparsed = new Vector();
        try {
            FileReader fr = new FileReader(jadname);
            BufferedReader br = new BufferedReader(fr);
            String temp = null;
            do {
                temp = br.readLine();
                if (temp != null) unparsed.addElement(temp);
            }
            while(temp != null);
        }
        catch (FileNotFoundException fnfe) {
            System.out.println("File not found: "+jadname);
            System.out.println("Please specify a valid path to your *.jad File");
        }
        catch (IOException ioe) {
            System.out.println(ioe.toString());
        }
        return unparsed;
    }
    
    /**
     *Splits the lines into keys and values;
     */
    private void split(Vector unparsed) {
        // I've tryed the StringTokenizer but it doesn't work properly/too difficult with URLs (http://...)
        for (int i=0; i<unparsed.size(); i++) {
            String buffer = (String) unparsed.elementAt(i);
            int colon_at;
            //Where is the the delimiter (colon ':') ?
            for (colon_at = 0; colon_at<buffer.length()-1; colon_at++) {
                if (buffer.charAt(colon_at) == ':') break;
            }
            //cuts the buffer into pieces
            String key = buffer.substring(0,colon_at); 
            String value = buffer.substring(colon_at+1,buffer.length());
            if (key.equals(null)) break;
            if (value.equals(null)) value ="";
            jadcontents.put(key,value);
        }
    }
   
    
    private Object[] splitMidlets() {
        //Keeps the Arrays below
        Vector midletlist = new Vector();
        // [0] Name, [1] Icon, [2] Class
        for (int i=1; i<10; i++) {
            String[] midlets = new String[3];
            if (jadcontents.containsKey("MIDlet-"+i)) {
                String temp = (String) jadcontents.get("MIDlet-"+i);
                StringTokenizer tokenizer = new StringTokenizer(temp,",");
                midlets[0] = tokenizer.nextToken().trim();
                midlets[1] = tokenizer.nextToken().trim();
                midlets[2] = tokenizer.nextToken().trim();
                midletlist.addElement(midlets);
            }
            else break;
        }
        /* For testing only
        for (int i=0; i<midletlist.size(); i++) {
            for (int j=0; j<3; j++) {
                String array[] = (String[])midletlist.elementAt(i);
                System.out.println(array[j]);
            }
        }
         */
        return midletlist.toArray();
    }

    
    //For testing only...
    public static void main(String args[]) {
        JAD_Parser jadparser = new JAD_Parser(args[0]);
    }
}
