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

import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public abstract class AbstractNode implements TreeNode {

    AbstractNode parent;
    Vector children;
    JTree tree;


    protected AbstractNode (AbstractNode parent, boolean mayHaveChildren) {
	this.parent = parent;
	if (mayHaveChildren) 
	    children = new Vector ();
    }


    public abstract Component getComponent ();


    public JTree getTree () {
	if (tree != null) return tree;
	if (parent != null) return parent.getTree ();
	return null;
    }


    public TreePath getPath () {
	return parent == null 
	    ? new TreePath (this)
		: parent.getPath ().pathByAddingChild (this);
    }
	

    public void add (AbstractNode node, boolean select) {
	if (node.parent != this) 
	    throw new RuntimeException ("inconsistent parent");

	children.addElement (node);
	
	JTree t = getTree (); 

	if (t != null) {
	    DefaultTreeModel model = (DefaultTreeModel) t.getModel (); 

	    model.nodeChanged (this);
	    model.nodesWereInserted (this, new int[] {children.size ()-1});
	    
	    TreePath path = node.getPath ();
	    t.makeVisible (path);
	    t.setSelectionPath (path);
	}
    }
	/*
	public RootNode getRoot () {
	    AbstractNode node = this;
	    while (node.parent != null) node = node.parent;
	    return (RootNode) node;
	    }*/

    public Enumeration children () {
	return children == null 
	    ? new Vector ().elements () 
		: children.elements ();
    }
    
    public boolean getAllowsChildren () {
	return children != null;
    }
    
    public TreeNode getChildAt (int index) {
	return (TreeNode) children.elementAt(index);
    }
    

    public int getChildCount () {
	return children == null ? 0 : children.size ();
    }

    public int getIndex (TreeNode n) {
	return children == null ? -1 : children.indexOf (n);
    }
    
    public TreeNode getParent () {
	return parent;
    }

    public boolean isLeaf () {
	return children == null || children.size () == 0;
    }

    public void remove () {
	if (parent != null) {
	    int i = parent.children.indexOf (this);
	    parent.children.removeElementAt (i);
	    
	    JTree t = getTree ();
	    
	    if (t != null) {
		DefaultTreeModel dtm = (DefaultTreeModel) t.getModel ();
		
		dtm.nodesWereRemoved (parent, new int[] {i}, new Object[]{this});
		
		t.setSelectionPath (parent.getPath ());
	    }
	}
	
	if (children != null) 
	    for (int i = 0; i < children.size (); i++) 
		((AbstractNode) children.elementAt (i)).remove ();
    }


    protected void selected () {
    }
}
    





