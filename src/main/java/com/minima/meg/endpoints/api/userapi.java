package com.minima.meg.endpoints.api;

import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			String command = row.getString("COMMAND").trim();
			
			//Convert all the params
			String pattern 	= "(\\$[a-zA-Z0-9]+)";
			
			//Create a regex matcher 
			Matcher matcher = Pattern.compile(pattern).matcher(command);

			//Create a new copy..
			String newcommand = command+"";
			while (matcher.find()) {
			  String word = matcher.group(1);
			  
			  //Remove the front $
			  String paramname = word.substring(1);
			  
			  //Get the param of that name..
			  String param = HTTPClientUtil.getValidParam(request,paramname);
			  
			  //Now replace that
			  newcommand = newcommand.replace(paramname, param); 
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
	
	public static void main(String[] zArgs) {
		
		String input 	= "lets find [$message{}] $hello $method}";
		//input 	= java.util.regex.Pattern.quote(input);

		String pattern 	= "(\\$[a-zA-Z0-9]+)";
		//pattern 		= java.util.regex.Pattern.quote(pattern);
		
		Matcher matcher = Pattern.compile(pattern).matcher(input);

		String newstring = input;
		while (matcher.find()) {
		  String word = matcher.group(1);
		  System.out.println("FOUND:"+word);
		  
		  newstring = newstring.replace(word, word+word); 
			
		  System.out.println("NEW STRING:"+newstring);
		}
		
	}
}

