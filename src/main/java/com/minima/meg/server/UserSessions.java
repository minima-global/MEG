package com.minima.meg.server;

import java.util.Hashtable;

import com.minima.meg.Log;

public class UserSessions {

	private static Hashtable<String, String> mUserSessions = new Hashtable<>();
	
	public static void addSession(String zSessionid, String zUser) {
		mUserSessions.put(zSessionid, zUser);	
	}
	
	public static String getUserFromSession(String zSessionid) {
		return mUserSessions.get(zSessionid);
	}
	
	public static void clearSession(String zSessionid) {
		mUserSessions.remove(zSessionid);
	}
	
	public static void printAllSessions() {
		Log.log("ALL SESSIONS : "+mUserSessions.toString());
	}
}
