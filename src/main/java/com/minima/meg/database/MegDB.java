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
	
	public MegDB(File zDatabaseFolder) {
		mDatabaseFolder = zDatabaseFolder;
		
		mUserDB = new UserDB();
	}
	
	public UserDB getUserDB() {
		return mUserDB;
	}
	
	public void loadAllDB() throws SQLException {
		
		Log.log("Loading all MEGDB");
		
		//Load the UserDB
		mUserDB.loadDB(new File(mDatabaseFolder,"userdb"));
	}
	
	public void saveAllDB() {
		Log.log("Saving all MEGDB");
		
		mUserDB.saveDB(true);
	}
}
