package org.kobjects.io;

import java.io.*;

public class BoundInputStream extends InputStream {

    int remaining;
    InputStream is;


    public BoundInputStream (InputStream is, int length) {
        this.is = is;
        this.remaining = length;
    }


    public int available () throws IOException {
        int avail = is.available ();
        return avail < remaining ? avail : remaining;
    }


    public int read () throws IOException{

        if (remaining <= 0) return -1;
        remaining--;
        return is.read ();
    }


    public int read (byte [] data, int start, int max) throws IOException {
        if (max > remaining) 
            max = remaining;

        int actual = is.read (data, start, max);

        if (actual > 0) remaining -= actual;

        return actual;
    }
}
