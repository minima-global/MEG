package com.minima.meg.endpoints.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.ApiCaller;
import com.minima.meg.utils.HTTPClientUtil;

public class userapi extends ApiCaller {

	public void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut) {
		
		//Get the call
		String apicall = request.getRequestURI().substring(5);
		
		//Find this call..
		JSONObject ep = MegDB.getDB().getUserDB().getEndpoiont(apicall);
		if(ep.getInt("count")==0) {
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("ENDPOINT NOT FOUND", apicall, zUser);
			
			//NOT FOUND..
			HTTPClientUtil.writeJSONError(zOut,"API Endpoint not found");
			return;
		}
		
		try {
			
			//Get it..
			JSONObject row = ep.getJSONArray("rows").getJSONObject(0);
			String command = row.getString("COMMAND");
			
			//Now replace the parameters
			String newcommand = new String(command+"");
			int pos=0;
			while(true) {
				int index = command.indexOf("$",pos);
				if(index == -1) {
					break;
				}
				
				//Find end space or end of sentence
				int endword = command.indexOf(" ",index);
				if(endword == -1) {
					endword = command.length();
				}
				
				//Now make the word
				String word = command.substring(index,endword);
				
				//Param name has no $
				String paramname 	= word.substring(1);
				String param 		= HTTPClientUtil.getValidParam(request,paramname);
				
				//Now replace..
				newcommand = newcommand.replace(word, ""+param);
				
				//Move counter along..
				pos = endword;
			}
		
			//Make the call to Minima..
			String res = HTTPClientUtil.runMinimaCMD(newcommand);
			zOut.println(res);
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("ENDPOINT CALL", apicall, zUser);
			
		} catch (Exception e) {
			HTTPClientUtil.writeJSONError(zOut,e.toString());
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("ENDPOINT CALL FAIL "+e, apicall, zUser);
		}
	}
}

