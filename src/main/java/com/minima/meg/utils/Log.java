package com.minima.meg.utils;

public class Log {

	public static boolean DEBUG_LOGGING_ENABLED = false;
	
	public static void log(String zLog) {
		System.out.println(zLog);
	}
	
	public static void debug(String zLog) {
		if(DEBUG_LOGGING_ENABLED) {
			System.out.println(zLog);
		}
	}
	
	
}
