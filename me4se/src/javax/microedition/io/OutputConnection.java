// STATUS: API complete

package javax.microedition.io;

import java.io.*;

public interface OutputConnection extends Connection {

    public DataOutputStream openDataOutputStream () throws IOException;
    public OutputStream openOutputStream () throws IOException;

}
