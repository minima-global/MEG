package com.minima.meg.database;

import java.io.File;
import java.io.IOException;
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
	PrefsDB mPrefsDB;
	
	public static int MINIMUM_KEYUSES = 0;
	NonceDB mNonceDB;
	
	//Is the admin account enabled..
	boolean mAdminEnabled = false;
	String mAdminPassword = "";
	
	//Is the apicaller enabled..
	boolean mApiCallerEnabled = false;
	String mApiCallerPassword = "";
		
	public MegDB(File zDatabaseFolder) {
		mDatabaseFolder = zDatabaseFolder;
		
		mUserDB = new UserDB();
		mLogsDB = new LogsDB();
		
		mPrefsDB = new PrefsDB();
		
		mNonceDB = new NonceDB();
	}
	
	public File getDatabaseFolder() {
		return mDatabaseFolder;
	}
	
	/**
	 * Is the admin account enabled..
	 */
	public void setAdminEnabled(boolean zEnable, String zPassword) {
		mAdminEnabled 	= zEnable;
		mAdminPassword 	= zPassword;
	}
	
	public boolean getAdminEnabled() {
		return mAdminEnabled;
	}
	
	public boolean checkAdminPassword(String zPassword) {
		return mAdminPassword.equals(zPassword);
	}
	
	/**
	 * Is the apicaller account enabled..
	 */
	public void setApiCallerEnabled(boolean zEnable, String zPassword) {
		mApiCallerEnabled 	= zEnable;
		mApiCallerPassword 	= zPassword;
	}
	
	public boolean getApiCallerEnabled() {
		return mApiCallerEnabled;
	}
	
	public boolean checkApiCallerPassword(String zPassword) {
		return mApiCallerPassword.equals(zPassword);
	}
	
	public UserDB getUserDB() {
		return mUserDB;
	}

	public LogsDB getLogsDB() {
		return mLogsDB;
	}
	
	public PrefsDB getPrefsDB() {
		return mPrefsDB;
	}
	
	public NonceDB getNonceDB() {
		return mNonceDB;
	}
	
	public void loadAllDB() throws SQLException, IOException {
		
		Log.log("Loading all MEGDB");
		
		//Load the UserDB
		mUserDB.loadDB(new File(mDatabaseFolder,"userdb"));
		mLogsDB.loadDB(new File(mDatabaseFolder,"logsdb"));
		mPrefsDB.loadDB(new File(mDatabaseFolder,"prefsdb"));
		mNonceDB.loadDB(new File(mDatabaseFolder,"noncedb"));
	}
	
	public void saveAllDB() throws IOException {
		Log.log("Saving all MEGDB");
		
		mUserDB.saveDB(true);
		mLogsDB.saveDB(true);
		mNonceDB.saveDB(true);
		
		mPrefsDB.saveDB(new File(mDatabaseFolder,"prefsdb"));
	}
}
