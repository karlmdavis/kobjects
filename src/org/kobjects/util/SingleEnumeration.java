package org.kobjects.util;

import java.util.Enumeration;

// (C) 2002 by Stefan Haustein 
// Rolandstrasse 27, D-46045 Oberhausen, Germany
// All rights reserved.
//
// For licensing details, please refer to the file "license.txt",
// distributed with this file.

/**
 * @author Stefan Haustein */
public class SingleEnumeration implements Enumeration {


	Object object;

    /**
     * Constructor for SingleEnumeration.
     */

    public SingleEnumeration(Object object) {
		this.object = object;
    }

    /**
     * @see java.util.Enumeration#hasMoreElements()
     */
    public boolean hasMoreElements() {
        return object != null;
    }

    /**
     * @see java.util.Enumeration#nextElement()
     */
    public Object nextElement() {
		Object result = object;
		object = null;
        return result;
    }

}
