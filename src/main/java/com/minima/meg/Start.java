package com.minima.meg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.minima.meg.database.MegDB;
import com.minima.meg.database.NonceDB;
import com.minima.meg.utils.Log;

/**
 * Hello world!
 *
 */
public class Start 
{
	/**
	 * The Main MEG Manager
	 */
	public static final String MEG_VERSION = "1.5";
	
	private static MEGManager mMEG;
	
	private static boolean mUseShutdownHook;
	
	public static void main( String[] zArgs ) throws Exception
    {
		//Default..
		int meg_port 		= 8080;
		String adminpass 	= "";
		File dataFolder   	= new File(System.getProperty("user.home"),".meg");
		int minkeyuses		= 0;		
		
    	//Get the start params..
		int arglen 	= zArgs.length;
		if(arglen > 0) {
			int counter	= 0;
			while(counter<arglen) {
				String arg 	= zArgs[counter];
				counter++;
				
				if(arg.equals("-port")) {
					meg_port = Integer.parseInt(zArgs[counter++]);
					
				}else if(arg.equals("-minkeyuses")) {
					minkeyuses = Integer.parseInt(zArgs[counter++]);
				
				}else if(arg.equals("-adminpassword")) {
					adminpass = zArgs[counter++];
				
				}else if(arg.equals("-data")) {
					dataFolder = new File(zArgs[counter++]);
				
				}else if(arg.equals("-help")) {
					
					System.out.println("MEG Help");
					System.out.println(" -port            : Specify the port to listen on");
					System.out.println(" -data            : Specify the data folder");
					System.out.println(" -adminpassword   : Specify the 'admin' User account password (use to add initial accounts)");
					System.out.println(" -minkeyuses      : Specify a MINIMUM key uses value for any Public Keys (if you are running this from a new server)");
					System.out.println(" -help            : Print this help");
					
					System.exit(1);
					
				}else {
					System.out.println("Unknown parameter : "+arg);
					System.exit(1);
				}
			}
		}
		
		//Disable Jetty logging
		System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
		System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
		
		//Use shutdown hook
		mUseShutdownHook = true;
		
		Log.log("MEG v"+MEG_VERSION);
		
        //Create and start server 
		mMEG = new MEGManager();
		mMEG.doStartUp(meg_port,adminpass,dataFolder);
		
		//Set Min Key uses..
		MegDB.getDB().MINIMUM_KEYUSES = minkeyuses;
		if(minkeyuses!=0) {
			Log.log("Minimum 'keyuses' set to  : "+minkeyuses);
		}
		
		//Run some tests..
        /*Log.log("Running some tests..");
        NonceDB ndb = MegDB.getDB().getNonceDB();
        Log.log("0xFFEE GEtINcKeys : "+ndb.getAndIncrementKeyUses("0xFFEE"));
        Log.log("0xFFEE GEtINcKeys : "+ndb.getAndIncrementKeyUses("0xFFEE"));
        Log.log("0xFFEE GEtINcKeys : "+ndb.getAndIncrementKeyUses("0xFFEE"));
        Log.log("0xFFEE GEtINcKeys : "+ndb.getAndIncrementKeyUses("0xFFEE"));
        Log.log("MAX : "+ndb.getMaxKeyUses());
        */
		
        //Add a shutdown hook..
        Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run(){
				
				if(!mUseShutdownHook) {
					return;
				}
				
				//Shutdown hook called..
				Log.log("[!] MEG Shutdown Hook..");
				
				//Shut down the whole system
				try {
					mMEG.doShutDown();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
        //Listen for input
		InputStreamReader mInputStream = new InputStreamReader(System.in);
		BufferedReader mBufferedStream = new BufferedReader(mInputStream);
      	    
  	    //Loop until finished..
  	    while(true){
  	        try {
  	            //Get a line of input
  	            String input = mBufferedStream.readLine();
  	            
  	            //Check valid..
  	            if(input!=null && !input.equals("")) {
  	            	//trim it..
  	            	input = input.trim();
  	            	       	
  	            	//Print it out
  	            	if(input.equals("quit") || input.equals("exit")) {
  	            		Log.log("Shutdown initiated..");
  	            		break;
  	            	}else {
  	            		Log.log("Unknown command : "+input);
  	            	}
  	            }
  	            
  	        } catch (Exception ex) {
  	            Log.log(ex.toString());
  	        }
  	    }
  	    
  	    //Cross the streams..
  	    try {
  	        mBufferedStream.close();
  	        mInputStream.close();
  	    } catch (IOException ex) {
  	    	Log.log(""+ex);
  	    }
  	    
  	 	try {
			mMEG.doShutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
  	    
  	    mUseShutdownHook = false;
  	    
  	    System.exit(0);
    }
}
