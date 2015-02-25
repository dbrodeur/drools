package org.drools.examples.helloworldaop;

public class UtilityClass {
	public static boolean trace( String msg ) {
		System.out.println(msg);
		return true;
	}
	
	public static boolean trace2( String msg ) {
		System.out.println(msg);
		return true;
	}
}
