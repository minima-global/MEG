package com.minima.meg.database;

import java.util.ArrayList;

import com.minima.meg.utils.Log;

public class CacheDB {

	ArrayList<CacheObject> mCachedData = new ArrayList<CacheObject>();
	
	public CacheDB() {}
	
	public synchronized void addCacheCall(String zCommand, String zResponse) {
		//Create a cached Object
		mCachedData.add(new CacheObject(zCommand, zResponse, System.currentTimeMillis()));
		//Log.log("Cache ADD : "+zCommand+" cachesize:"+mCachedData.size());
	}
	
	public synchronized String getCacheData(String zCommand) {
		
		//Current time..
		long ctime = System.currentTimeMillis();
		
		//New List
		ArrayList<CacheObject> newlist = new ArrayList<CacheObject>();
		
		//Did we find it..
		CacheObject foundcache = null;
		
		//Cycle through the whole cache..
		for(CacheObject cobj : mCachedData) {
		
			//Is this the data..
			if(cobj.mCommand.equals(zCommand)) {
				foundcache = cobj;
			}
			
			//Check time.. do we keep it..
			if(cobj.mTimeMilli + 60000 > ctime) {
				newlist.add(cobj);
			}
		}
		
		//Now switch back
		mCachedData = newlist;
		
		if(foundcache != null) {
			//Log.log("Cache Found : "+zCommand+" cachesize:"+mCachedData.size());
			return foundcache.mResponse;
		}else {
			//Log.log("Cache NOT Found : "+zCommand+" cachesize:"+mCachedData.size());
		}
		
		return null;
	}
}
