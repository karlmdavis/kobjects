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

public final class Util {

/** 
 * Writes the contents of the input stream to the 
 * given output stream and closes the input stream.
 * The output stream is returned */

public static OutputStream streamcopy(InputStream is, OutputStream os)
    throws IOException {
    byte[] buf =
        new byte[Runtime.getRuntime().freeMemory() >= 1048576 ? 16384 : 128];
    while (true) {
        int count = is.read(buf, 0, buf.length);
        if (count == -1)
            break;
        os.write(buf, 0, count);
    }
    is.close();
    return os;
}

public static String buildUrl(String base, String local) {

    int ci = local.indexOf(':');

    // slash or 2nd char colon: ignore base, return file://local

    if (local.startsWith("/") || ci == 1)
        return "file:///" + local;

    // local contains colon, assume URL, return local

    if (ci > 2 && ci < 6)
        return local;

    if (base == null)
        base = "file:///";
    else {
        if (base.indexOf(':') == -1)
            base = "file:///" + base;

        if (!base.endsWith("/"))
            base = base + ("/");
    }

    return base + local;
}


}
