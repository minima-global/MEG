package com.minima.meg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.database.NonceDB;
import com.minima.meg.utils.HTTPClientUtil;
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
	public static final String MEG_VERSION = "2.6.6";
	
	private static MEGManager mMEG;
	
	private static boolean mUseShutdownHook;
	
	public static void main( String[] zArgs ) throws Exception
    {
		
		//Get Environment vars..
		Map<String, String> envs = System.getenv();
		
		//Default.. check environment variables..
		int meg_port 		= getEnvVarIntIfExists(envs,"meg_port",8080);
				
		String adminpass 	= getEnvVarIfExists(envs,"meg_adminpassword");
		
		File dataFolder   	= new File(System.getProperty("user.home"),".meg");
		String dataset 		= getEnvVarIfExists(envs,"meg_data");
		if(!dataset.equals("")) {
			dataFolder = new File(dataset);
		}
		
		int minkeyuses				= getEnvVarIntIfExists(envs,"meg_minkeyuses",0);		
		
		String meghost				= getEnvVarIfExists(envs,"meg_meghost");
		String minimarpc   			= getEnvVarIfExists(envs,"meg_minimarpc");
		String minimarpcpassword   	= getEnvVarIfExists(envs,"meg_minimarpcpassword");
		
		String apicallerpass 		= getEnvVarIfExists(envs,"meg_apicallerpassword");

		int delay = getEnvVarIntIfExists(envs,"meg_startupdelay",0);

    	//Now Get the start params.. from command line
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
				
				}else if(arg.equals("-startupdelay")) {
					delay = Integer.parseInt(zArgs[counter++]);
				
				}else if(arg.equals("-meghost")) {
					meghost = zArgs[counter++];
					
				}else if(arg.equals("-minimarpc")) {
					minimarpc = zArgs[counter++];
					
				}else if(arg.equals("-minimarpcpassword")) {
					minimarpcpassword = zArgs[counter++];
				
				}else if(arg.equals("-adminpassword")) {
					adminpass = zArgs[counter++];
				
				}else if(arg.equals("-apicallerpassword")) {
					apicallerpass = zArgs[counter++];
				
				}else if(arg.equals("-logs")) {
					Log.LOGGING_ENABLED = true;
					
				}else if(arg.equals("-data")) {
					dataFolder = new File(zArgs[counter++]);
				
				}else if(arg.equals("-help")) {
					
					System.out.println("MEG Help");
					System.out.println(" -port                : The port to listen on");
					System.out.println(" -data                : The data folder");
					System.out.println(" -adminpassword       : The 'admin' User account password (can then add other accounts)");
					System.out.println(" -apicallerpassword   : The 'apicaller' User account password");
					System.out.println(" -meghost             : The http://host:port of this instance of MEG");
					System.out.println(" -minimarpc           : The http://host:port of the Minima RPC");
					System.out.println(" -minimarpcpassword   : The Minima RPC password if enabled");
					System.out.println(" -minkeyuses          : MINIMUM key uses value for any Public Keys (if you are running this from a new server)");
					System.out.println(" -startupdelay        : Wait X ms before starting up (giving time to Minima to start) - used in Docker Container..");
					//System.out.println(" -logs                : Enable Debug logs)");
					System.out.println(" -help                : Print this help");
					
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
		
		//Is there a delay
		if(delay != 0) {
			Log.log("Startup Delay : "+delay+" ms..");
			Thread.sleep(delay);
		}
		
        //Create and start server 
		mMEG = new MEGManager();
		mMEG.doStartUp(meg_port,adminpass,dataFolder);
		
		//Set Min Key uses..
		MegDB.getDB().MINIMUM_KEYUSES = minkeyuses;
		if(minkeyuses!=0) {
			Log.log("Minimum 'keyuses' set to  : "+minkeyuses);
		}
		
		//Now set the deaults if set
		if(!meghost.equals("")) {
			
			if(!meghost.endsWith("/")) {
				meghost = meghost+"/";
			}
			
			MegDB.getDB().getPrefsDB().setMEGNode(meghost);
		}
		if(!minimarpcpassword.equals("")) {
			MegDB.getDB().getPrefsDB().setMinimaRPCPassword(minimarpcpassword);
		}
		
		if(!minimarpc.equals("")) {
			MegDB.getDB().getPrefsDB().setMinimaNode(minimarpc);
		
			//Set the WEBHOOK
			try {
				String cmd = "webhooks action:add hook:"+meghost+"webhook";
				String res = HTTPClientUtil.runMinimaCMD(cmd);
				
				JSONObject hook = new JSONObject(res);
				if(!hook.getBoolean("status")) {
					throw new Exception("Failed to set webhook");
				}
				
				Log.log("Minima WEBHOOK set correctly from command line params @ "+minimarpc);
				
			} catch (Exception e) {
				Log.log(e.toString());
				
				System.exit(1);
			}
		}
		
		if(!apicallerpass.equals("")) {
			MegDB.getDB().setApiCallerEnabled(true, apicallerpass);
			Log.log("Default APICaller password set..");
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
	
	public static String getEnvVarIfExists(Map<String, String> zEnv, String zName) {
		String val = zEnv.get(zName);
		if(val == null) {
			return "";
		}
		return val;
	}
	
	public static int getEnvVarIntIfExists(Map<String, String> zEnv, String zName, int zDefault) {
		String val = zEnv.get(zName);
		if(val == null) {
			return zDefault;
		}
		return Integer.parseInt(val);
	}
}
