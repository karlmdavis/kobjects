package org.kobjects.pim;

/**
 * @author haustein
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class VCard extends PimItem {

	
	public int getType(String name) {
		if (name.equals("n") || name.equals("adr")) return TYPE_STRING_ARRAY;
		return TYPE_STRING; 
	}	
	
	public String getType() {
		return "vcard";
	}
}
