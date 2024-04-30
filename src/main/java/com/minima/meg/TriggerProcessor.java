package com.minima.meg;

import com.minima.meg.utils.Log;
import com.minima.meg.utils.messages.Message;
import com.minima.meg.utils.messages.MessageProcessor;

public class TriggerProcessor extends MessageProcessor{

	private static TriggerProcessor mTriggerLink;
	public static TriggerProcessor getTriggerManager() {
		return mTriggerLink;
	}
	
	public static String NEW_MINIMA_EVENT = "NEW_MINIMA_EVENT";
	
	public TriggerProcessor() {
		super("TRIGGERS");
		
		//Set this for others to get hold of
		mTriggerLink = this;
	}
	
	@Override
	protected void processMessage(Message zMessage) throws Exception {
		
		if(zMessage.getMessageType().equals(NEW_MINIMA_EVENT)) {
			
			//Get the JSON..
			Log.log("WEBHOOK "+zMessage.getString("event"));
			
			
			
		}
	}

}
