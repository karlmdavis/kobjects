package javax.microedition.io;


public interface StreamConnectionNotifier extends Connection {
    
    public StreamConnection acceptAndOpen () throws java.io.IOException;
    
}
