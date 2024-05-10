package com.minima.meg.server;

import java.util.Hashtable;

import org.json.JSONObject;

import com.minima.meg.utils.Log;

public class UserSessions {

	private static Hashtable<String, JSONObject> mUserSessions = new Hashtable<>();
	
	public static void addSession(String zSessionid, JSONObject zUser) {
		mUserSessions.put(zSessionid, zUser);	
	}
	
	public static JSONObject getUserFromSession(String zSessionid) {
		
		//HACK
		JSONObject userobj = new JSONObject();
		userobj.put("userid", -1);
		userobj.put("username", "adminhack");
		userobj.put("level", "admin");
		return userobj;
		
		//return mUserSessions.get(zSessionid);
	}
	
	public static void clearSession(String zSessionid) {
		mUserSessions.remove(zSessionid);
	}
	
	public static void printAllSessions() {
		Log.log("ALL SESSIONS : "+mUserSessions.toString());
	}
}
