// STATUS: API complete

package javax.microedition.io;

import java.io.*;

public interface InputConnection extends Connection {

    public DataInputStream openDataInputStream () throws IOException;
    public InputStream openInputStream () throws IOException;

}
