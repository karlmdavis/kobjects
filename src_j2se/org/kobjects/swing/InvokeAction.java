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

import java.lang.reflect.*;
import java.awt.event.*;
import javax.swing.*;
import org.kobjects.util.*;

public class InvokeAction extends AbstractAction {

    String title;
    Object target;
    Method method;
    boolean parameter;

    public InvokeAction (String title, Object target, String methodName) {
	super (title);
	this.title = title;
	this.target = target;
	try {
	    this.method = target.getClass ().getMethod 
		(methodName, new Class[] {ActionEvent.class});
	    parameter = true;
	}
	catch (NoSuchMethodException e) {
	    try {
		this.method = target.getClass ().getMethod 
		    (methodName, new Class[0]); 
	    }
	    catch (NoSuchMethodException e2) {
		throw new RuntimeException 
		    ("found neither "+methodName 
		     + "(java.awt.event.ActionEvent) nor "+methodName
		     +"() in "+target.getClass ());
	    }
	}
    }


    public InvokeAction (String name, Object target) {

	this (name, target, name);
    }


    public void actionPerformed (ActionEvent ev) {
	try {
	    method.invoke (target, parameter 
			   ? new Object []{ev} 
			   : new Object [0]);
	}
	catch (Exception e) {
	    throw ChainedRuntimeException.create 
		(e, this.toString ());
	}
    }

    public String toString () {
	return "Action: "+ title + " target: "+target + " method: "+method;
    }
}

