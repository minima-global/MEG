package com.minima.meg;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.utils.HTTPClient;
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
			String event 	= zMessage.getString("event");
			JSONObject data = (JSONObject) zMessage.getObject("data");
			String datastr	= data.toString();
			
			//Get all the events we are listening for..
			JSONObject alltriggers = MegDB.getDB().getUserDB().getAllTriggers();
			
			int rows = alltriggers.getInt("count");
			for(int i=0;i<rows;i++) {
				JSONObject row = alltriggers.getJSONArray("rows").getJSONObject(i);
			
				String trigger 		= row.getString("TRIGGER");
				String extradata 	= row.getString("EXTRADATA");
				String url 			= row.getString("URL");
						
				boolean callit = false;
				
				if(trigger.equals("NEWBLOCK") && event.equals("NEWBLOCK")) {
					callit = true;
					
				}else if(trigger.equals("NEWBALANCE") && event.equals("NEWBALANCE")) {
					callit = true;
					
				}else if(trigger.equals("NEWTXPOW") && event.equals("NEWTXPOW")) {
					callit = true;
					
				}else if(trigger.equals("ADDRESS_USED") && event.equals("NEWTXPOW")) {
					
					//Check for ExtraData
					if(datastr.contains(extradata)) {
						callit = true;
					}
					
				}else if(trigger.equals("TOKEN_USED") && event.equals("NEWTXPOW")) {
					
					//Check for ExtraData
					if(datastr.contains(extradata)) {
						callit = true;
					}
				}
				
				//Valid trigger ?
				if(callit) {
					
					//Trigger Detais
					JSONObject triggerjson = new JSONObject();
					triggerjson.put("trigger", trigger);
					triggerjson.put("extradata", extradata);
					triggerjson.put("url", url);
					
					//The Minima Event
					JSONObject minima = new JSONObject();
					minima.put("event", event);
					minima.put("data", data);
					
					//The full packet
					JSONObject fulldata = new JSONObject();
					fulldata.put("meg", triggerjson);
					fulldata.put("minima", minima);
					
					//And now POST it..
					HTTPClient.POST("http://127.0.0.1:8080/testpost", fulldata.toString());
				
					//Add a log
					MegDB.getDB().getLogsDB().addLog("TRIGGER EVENT", trigger, "");
				}
			}
		}
	}
	
	

}
