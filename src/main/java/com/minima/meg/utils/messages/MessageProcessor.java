/**
 * 
 */
package com.minima.meg.utils.messages;

import com.minima.meg.utils.Log;

/**
 * @author Spartacus Rex
 *
 */
public abstract class MessageProcessor extends MessageStack implements Runnable{

	/**
	 * Main Thread loop
	 */
    private Thread mMainThread;
    
    /**
     * Are we running
     */
    private boolean mRunning;
    
    /**
     * Have we finished shutting down
     */
    private boolean mShutDownComplete;
    
	/**
	 * LOG messages ?
	 */
	static private boolean mTrace 		= false;
	static private String mTraceFilter 	= "";
	
	public static void setTrace(boolean zTrace, String zTraceFilter) {
		mTrace 			= zTrace;
		mTraceFilter 	= zTraceFilter;
	}
	
	/**
	 * Processor Name
	 */
	String mName;
	
	/**
	 * Constructor
	 */
    public MessageProcessor(String zName){
    	super();
    	
    	mName 				= zName;
    	mRunning 			= true;
    	mShutDownComplete 	= false;
    	
    	mMainThread = new Thread(this,zName);
        mMainThread.start();
    }
    
    public void setFullLogging(boolean zLogON, String zTraceFilter) {
    	mTrace 			= zLogON;
    	mTraceFilter 	= zTraceFilter;
    }
    
    public boolean isTrace() {
    	return mTrace;
    }
    
    public String getTraceFilter() {
    	return mTraceFilter;
    }
    
    public boolean isRunning(){
    	return mRunning;
    }
    
    public boolean isShutdownComplete() {
    	return mShutDownComplete;
    }
    
    public void waitToShutDown() {
    	long timewaited = 0;
    	while(!isShutdownComplete()) {
			try {Thread.sleep(250);} catch (InterruptedException e) {}
			timewaited +=250;
			if(timewaited>15000) {
				Log.log("Failed to shutdown in 10 secs for "+mName);
				mMainThread.interrupt();
				return;
			}
		}
    }
    
    public void stopMessageProcessor(){
        mRunning = false;
        
        //Wake it up if is locked..
        notifyLock();
    }
    
    public void run() {
    	
    	if(mTrace) {
    		Log.log("["+mMainThread.getName()+"] (stack:"+getSize()+") START");
        }
    	
    	//Loop while still running
        while(mRunning){
            //Check for valid mnessage
            Message msg = getNextMessage();
            
            //Cycle through available messages
            while(msg != null && mRunning){          
                //Process that message
                try{
                	//Are we logging  ?
                	long timenow = 0;
                	if(mTrace) {
                		timenow = System.currentTimeMillis();
                	}
                
                	//Process Message
                    processMessage(msg);
                
                    if(mTrace) {
                    	long timediff = System.currentTimeMillis() - timenow;
                    	String tracemsg = msg.toString();
                		if(tracemsg.contains(mTraceFilter)) {
                			Log.log("["+mMainThread.getName()+"] (stack:"+getSize()+") time:"+timediff+" \t"+msg);
                		}
                    }
                    
                }catch(Error noclass){
                	Log.log("**SERIOUS ERROR "+msg.getMessageType()+" "+noclass.toString());
                	
                	//Now the Stack Trace
            		for(StackTraceElement stack : noclass.getStackTrace()) {
            			//Print it..
            			Log.log("     "+stack.toString());
            		}
            		
                }catch(Exception exc){
                	exc.printStackTrace();
                } 
                
                //Are there more messages..
                msg = getNextMessage();
            }
            
            //Wait.. for a notify.. 
            try {
            	synchronized (mLock) {
            		//Last check.. inside the LOCK
            		if(!isNextMessage() && mRunning) {
            			//Wait for a message to be posted on the stack
            			mLock.wait();	
            		}
				}
			} catch (InterruptedException e) {
				Log.log("MESSAGE_PROCESSOR "+mName+" INTERRUPTED");
			}
        }

        if(mTrace) {
        	Log.log("["+mMainThread.getName()+"] (stack:"+getSize()+") SHUTDOWN");
        }
        
        //All done..
        mRunning 			= false;
        mShutDownComplete 	= true;
    }
    
    /**
     * This is the main processing unit, must be overloaded in extends classes
     * @param zMessage The Full Message
     * @throws Exception
     */
    protected abstract void processMessage(Message zMessage) throws Exception;
}

