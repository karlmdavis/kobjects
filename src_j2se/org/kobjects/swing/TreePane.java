package org.kobjects.swing;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;


public class TreePane extends JSplitPane implements TreeSelectionListener {

    JTree tree;
    AbstractNode root;
    Component current;
    JPanel container = new JPanel (new BorderLayout ());
    
    public TreePane () {
	super (JSplitPane.HORIZONTAL_SPLIT);
    }


    public TreePane (AbstractNode root) {
	this ();
	setRoot (root);
    }

    public AbstractNode getRoot () {
	return root;
    }
    
    public void setRoot (AbstractNode root) {
	tree = new JTree (root);
	this.root = root;
	root.tree = tree;
	JScrollPane sp = new JScrollPane (tree);
	sp.setPreferredSize (new Dimension (150, 300));
	setLeftComponent (new JScrollPane (sp));
	setRightComponent (container);

	setCurrent (root);

	tree.addTreeSelectionListener (this);
    }

    public void setCurrent (AbstractNode node) {
	container.removeAll ();
	container.add (node.getComponent ());
	node.selected ();
	container.validate ();
	container.repaint ();
    }


    public void valueChanged (TreeSelectionEvent e) {

	AbstractNode node = (AbstractNode) e.getPath ().getLastPathComponent ();
	setCurrent (node);

	//	int l = getDividerLocation ();
	//	setRightComponent (node.getComponent ());
	//setDividerLocation (l);


	//	root.add (current, BorderLayout.CENTER);
	//root.validate ();
	//current.repaint ();
    }
}



