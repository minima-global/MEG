package com.minima.meg;

import java.io.File;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.JettyServer;
import com.minima.meg.utils.Log;

public class MEGManager {

	private JettyServer mMainServer;
	
	public MEGManager() {}
	
	public void doStartUp() throws Exception {
		
		//Get the user folder
		File dataFolder   = new File(System.getProperty("user.home"),".meg");
		
		Log.log("MEG Startup data folder:"+dataFolder.getAbsolutePath());
		
		//Create the MegDB
		MegDB.createDB(dataFolder);
		
		//Load all the databases
		MegDB.getDB().loadAllDB();
		
		//Create and start Main server 
        mMainServer = new JettyServer();
        mMainServer.start();
	}
	
	public void doShutDown() throws Exception {
		
		//Save all the DB
		MegDB.getDB().saveAllDB();
				
		//Shut down the server
		mMainServer.stop();
		
		Log.log("MEG Shutdown complete..");
	}
}
