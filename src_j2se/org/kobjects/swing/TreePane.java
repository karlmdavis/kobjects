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

package org.kobjects.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


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



