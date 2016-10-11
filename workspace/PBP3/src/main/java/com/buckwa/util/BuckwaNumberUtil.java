package com.buckwa.util;

public class BuckwaNumberUtil {
	
	public static boolean isBetween(int a, int b, int c) {
	    return b > a ? c >= a && c <= b : c > b && c < a;
	}

}
