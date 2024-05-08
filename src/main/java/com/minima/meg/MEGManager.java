package com.minima.meg;

import java.io.File;

import com.minima.meg.database.MegDB;
import com.minima.meg.endpoints.trigger.TriggerProcessor;
import com.minima.meg.server.JettyServer;
import com.minima.meg.utils.Log;

public class MEGManager {

	private JettyServer mMainServer;
	
	private TriggerProcessor mTriggerProcessor;
	
	public MEGManager() {}
	
	public void doStartUp(int zPort, String zAdminPassword, File zDataFolder) throws Exception {
		
		Log.log("MEG data folder:"+zDataFolder.getAbsolutePath());
		
		//Create the MegDB
		MegDB.createDB(zDataFolder);
		
		//Load all the databases
		MegDB.getDB().loadAllDB();
		
		//Is Admin account enabled
		if(!zAdminPassword.equals("")) {
			MegDB.getDB().setAdminEnabled(true, zAdminPassword);
			Log.log("Default Admin account enabled");
		}else {
			Log.log("Default Admin account NOT enabled");
		}
		
		//Create the Trigger Backend Processor
		mTriggerProcessor = new TriggerProcessor();
		
		//Create and start Main server 
        mMainServer = new JettyServer();
        mMainServer.start(zPort);
	}
	
	public void doShutDown() throws Exception {
		
		//Stop the Trigger Thread
		mTriggerProcessor.stopMessageProcessor();
		
		//Save all the DB
		MegDB.getDB().saveAllDB();
				
		//Shut down the server
		mMainServer.stop();
		
		Log.log("MEG Shutdown complete..");
	}
}
