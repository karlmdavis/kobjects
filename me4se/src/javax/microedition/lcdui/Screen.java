package javax.microedition.lcdui;

public abstract class Screen extends Displayable {

    java.awt.Label title;

    Screen (String title) {
	this.title = new java.awt.Label (title);
	panel.add ("North", this.title);
    }

}
