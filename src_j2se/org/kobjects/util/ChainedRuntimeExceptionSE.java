package org.kobjects.util;

import java.io.*;

public class ChainedRuntimeExceptionSE
    extends ChainedRuntimeException {

    public ChainedRuntimeExceptionSE() {

    }

    /**
     * Constructor for ChainedRuntimeExceptionSE.
     * @param e
     * @param s
     */
    ChainedRuntimeExceptionSE(Exception e, String s) {
        super(e, s);
    }
    /* prints the own stack trace followed by the stack trace of the
    original exception to the given PrintStream */

    public void printStackTrace(PrintStream p) {
        super.printStackTrace(p);
        if (chain != null)
            chain.printStackTrace(p);
    }

    /* prints the own stack trace followed by the stack trace of the
    original exception to the given PrintWriter */

    public void printStackTrace(PrintWriter p) {
        super.printStackTrace(p);
        if (chain != null)
            chain.printStackTrace(p);
    }

    ChainedRuntimeException _create(Exception e, String s) {
        return new ChainedRuntimeExceptionSE(e, s);
    }
}
