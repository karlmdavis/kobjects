/*
 * Created on 17.04.2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.kobjects.util;

import java.text.Collator;
import java.util.Comparator;

/**
 * @author haustein
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GeneralComparator implements Comparator{

	static Collator collator = Collator.getInstance();
	static GeneralComparator instance = new GeneralComparator();


	public int compare(Object o1, Object o2) {
				
		if(o1 == null) return o2 == null ? 0 : -1;
		if(o2 == null) return 1;
				
		Class cl1 = o1.getClass();
		Class cl2 = o2.getClass();
				
		if((cl1 != cl2) && (o1 instanceof Number) && (o2 instanceof Number)) {
			double d1 = ((Number) o1).doubleValue();
			double d2 = ((Number) o2).doubleValue();
				
			return d1 == d2 ? 0 : (d1 > d2 ? 1 : -1);	
		}
			
		// TODO: Look up where Strings are actually trimmed in SQL.... 

		if(cl1 == String.class && cl2 == String.class){
			String s1 = (String) o1;
			String s2 = (String) o2;

			int c1 = 0;
			int c2 = 0;
			int l1 = s1.length();
			int l2 = s2.length();
            
			while(l1 > 0 && s1.charAt(l1-1) == ' ')
				l1--;

			while(l2 > 0 && s2.charAt(l2-1) == ' ')
				l2--;

			int i0=0;
			int l = l1 < l2 ? l1 : l2;
			while(i0 < l && s1.charAt(i0) == s2.charAt(i0)){
				i0++;
			}

			int delta;

			if(i0 == l) {
				delta = l1-l2;				
			}
			else if(s1.charAt(i0)<'A' || s2.charAt(i0)<'A')
				delta = s1.charAt(i0)-s2.charAt(i0);
			else {
				delta = collator.compare(s1.substring(i0, l1), s2.substring(i0, l2));
			}
            
			if (delta < -1) delta = -1;
			else if(delta > 1) delta = 1;
            
		  //     System.out.println("comparing '"+s1+"' and '"+ s2+"': "+delta);
			return delta;
		}
		
		if(o1 instanceof Comparable){
			try{
			return  ((Comparable) o1).compareTo(o2);
			}
			catch(Exception e){
			}
		}
				
		if(o1.equals(o2)) return 0;
		
		int cmp = compare(o1.toString(), o2.toString()); //Math.min(Math.max(o1.hashCode()-o2.hashCode(), -1), 1);
		return cmp == 0 ? 1 : cmp; // avoid 0 if equals returned false!
	}

	public static int cmp(Object o1, Object o2){
		return instance.compare(o1, o2);
	}


	public static GeneralComparator getInstance()	{
		return instance;
	}

}
