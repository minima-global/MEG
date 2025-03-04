package com.minima.meg.utils;

public class Log {

	public static boolean LOGGING_ENABLED = false;
	
	public static void log(String zLog) {
		if(LOGGING_ENABLED) {
			System.out.println(zLog);
		}
	}
}
