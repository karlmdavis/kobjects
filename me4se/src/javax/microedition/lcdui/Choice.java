// State: Complete

package javax.microedition.lcdui;


public interface Choice {

    public static final int EXCLUSIVE = 1;
    public static final int MULTIPLE = 2;
    public static final int IMPLICIT = 3;

    public int append (String s, Image i);
   
    public void delete (int index);
    
    public Image getImage (int index);

    public int getSelectedFlags (boolean[] flags);
    
    public int getSelectedIndex ();
    
    public String getString (int index);

    public void insert (int index, String stringItem, Image imageItem);

    public boolean isSelected (int index);

    public void set (int index, String str, Image img);
 
    public void setSelectedFlags (boolean [] flags);

    public void setSelectedIndex (int i, boolean state);
    
    public int size ();
}
