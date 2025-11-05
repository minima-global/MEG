package com.minima.meg.database;

public class CacheObject {

	public long 	mTimeMilli;
	public String 	mCommand;
	public String 	mResponse;
	
	public CacheObject(String zCommand, String zResponse, long zTimeMilli) {
		mTimeMilli 	= zTimeMilli;
		mCommand 	= zCommand;
		mResponse	= zResponse;
	}
}
