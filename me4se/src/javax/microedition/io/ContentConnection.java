package javax.microedition.io;

public interface ContentConnection extends StreamConnection {

    public String getType ();
    
    public long getLength ();

    public String getEncoding ();

}
