/* Copyright (c) 2002,2003, Stefan Haustein, Oberhausen, Rhld., Germany
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The  above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE. */

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
