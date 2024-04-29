package com.minima.meg.database;

import java.io.File;
import java.sql.SQLException;

import com.minima.meg.utils.Log;

public class MegDB {

	/**
	 * Give access to all classes via static method
	 */
	private static MegDB mMEGDB = null;
	public static void createDB(File zConfigFolder) {
		mMEGDB = new MegDB(zConfigFolder);
	}
	public static MegDB getDB() {
		return mMEGDB;
	}
	
	
	//Where are the files stored 
	File mDatabaseFolder;
	
	//The Databases
	UserDB mUserDB;
	LogsDB mLogsDB;
	
	public MegDB(File zDatabaseFolder) {
		mDatabaseFolder = zDatabaseFolder;
		
		mUserDB = new UserDB();
		mLogsDB = new LogsDB();
	}
	
	public UserDB getUserDB() {
		return mUserDB;
	}

	public LogsDB getLogsDB() {
		return mLogsDB;
	}
	
	public void loadAllDB() throws SQLException {
		
		Log.log("Loading all MEGDB");
		
		//Load the UserDB
		mUserDB.loadDB(new File(mDatabaseFolder,"userdb"));
		mLogsDB.loadDB(new File(mDatabaseFolder,"logsdb"));
		
	}
	
	public void saveAllDB() {
		Log.log("Saving all MEGDB");
		
		mUserDB.saveDB(true);
		mLogsDB.saveDB(true);
	}
}
