package org.kobjects.util;

/** This class is intended to be thrown in an exception handler, e.g.
    in order to wrap an IOException.  It adds the original exception
    stack trace to it's own stack trace output when printed. */

public class ChainedRuntimeException extends RuntimeException {

	Exception chain;

	public static ChainedRuntimeException create (Exception e, String s) {
		try {
		  return ((ChainedRuntimeException) Class.forName 
		  	("org.kobjects.util.ChainedRuntimeExceptionSE").newInstance ())._create (e, s);
		}
		catch (Exception x) {
	//	System.out.println (""+x);
		}
		return new ChainedRuntimeException (e, s);
	}

 
	ChainedRuntimeException () {
	}


	/** creates a new chained runtime exception with 
	additional information */

    ChainedRuntimeException(Exception e, String s) {
		super(((s == null) ? "rethrown" : s) + ": " + e.toString());
		chain = e;
	}

	/*creates a new chained runtime exception from the
	given exception. 

	public ChainedRuntimeException(Exception e) {
		this(e, null);
	}
    */
	
	/** Helper method to preserve stack trace in J2SE */
	
	ChainedRuntimeException _create (Exception e, String s) {
		throw new RuntimeException ("ERR!");	
	}


	/** returns the original exception */

	public Exception getChained() {
		return chain;
	}



	/** prints the own stack trace followed by the stack trace of the
	original exception. */

	public void printStackTrace() {
		super.printStackTrace();
		if (chain != null)
			chain.printStackTrace();
	}
}