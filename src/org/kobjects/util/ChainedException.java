package org.kobjects.util;

import java.io.*;


/** This class is intended to be thrown in an exception handler, e.g.
    in order to wrap an IOException.  It adds the original exception
    stack trace to it's own stack trace output when printed. */


public class ChainedException extends RuntimeException {

    Exception chain;



    /** creates a new chained runtime exception with 
	additional information */

    public ChainedException (Exception e, String s) { 
	super (((s==null) ? "rethrown" : s) + ": " + e.toString ()); 
	chain = e;
    }
    

    /** creates a new chained runtime exception from the
	given exception. */

    public ChainedException (Exception e) {
	this (e, null);
    }


    /** returns the original exception */

    public Exception getChained () {
	return chain;
    }


    /* prints the own stack trace followed by the stack trace of the
	original exception to the given PrintStream 

    public void printStackTrace (PrintStream p) {
	super.printStackTrace (p);
	if (chain != null) 
	    chain.printStackTrace (p);
    } */


    /* prints the own stack trace followed by the stack trace of the
	original exception to the given PrintWriter 

    public void printStackTrace (PrintWriter p) {
	super.printStackTrace (p);
	if (chain != null)
	    chain.printStackTrace (p);
    }*/

    /** prints the own stack trace followed by the stack trace of the
	original exception. */

    public void printStackTrace () {
	super.printStackTrace ();
	if (chain != null) 
	    chain.printStackTrace ();
    }
}

