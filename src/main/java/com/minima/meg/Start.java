package com.minima.meg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
	private static MEGManager mMEG;
	
	private static boolean mUseShutdownHook;
	
	public static void main( String[] args ) throws Exception
    {
    	/*HttpClient client = new HttpClient();
        client.start();
        ContentResponse res = client.GET("http://127.0.0.1:10005/block");
        System.out.println(res.getContentAsString());
        client.stop();*/
    	
		//Use shutdown hook
		mUseShutdownHook = true;
		
        //Create and start server 
		mMEG = new MEGManager();
		mMEG.doStartUp();
		
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
  	            	if(input.equals("quit")) {
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
