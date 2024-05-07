package com.minima.meg.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;

public class ApiCaller extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        doAppiCall("GET",request, "user", out);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        doAppiCall("POST",request, "user", out);
	}
	
	public void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut) {
		
		//Check the Auth token is a valid api-caller
		String auth = request.getHeader("Authorization");
		
		//Get the user session
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		
		String apicall = request.getRequestURI().substring(5);
		
		//Find this call..
		JSONObject ep = MegDB.getDB().getUserDB().getEndpoiont(apicall);
		if(ep.getInt("count")==0) {
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("ENDPOINT NOT FOUND", apicall, usersesh.getString("username"));
			
			//NOT FOUND..
			JSONObject resp = new JSONObject();
			resp.put("status", false);
			resp.put("error", "API Endpoint not found");
			resp.put("endpoint", apicall);
			zOut.println(resp.toString());
			return;
		}
		
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
			String paramname = word.substring(1);
			String param = request.getParameter(paramname);
			
			if(param == null) {
				
				//Add a DB LOG
				MegDB.getDB().getLogsDB().addLog("ENDPOINT PARAM MISSING", apicall+" param:"+paramname, usersesh.getString("username"));
				
				//NOT FOUND..
				JSONObject resp = new JSONObject();
				resp.put("status", false);
				resp.put("error", "API Endpoint Param missing : "+paramname);
				resp.put("endpoint", apicall);
				zOut.println(resp.toString());
				return;
			}
			
			//Now replace..
			newcommand = newcommand.replace(word, ""+param);
			
			//Move counter along..
			pos = endword;
		}
		
		//Make the call to Minima..
		//..
		
		//And return the result..
		//..
		
		JSONObject resp = new JSONObject();
		resp.put("status", true);
		zOut.println(resp.toString());
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("ENDPOINT CALL", apicall, usersesh.getString("username"));		
	}
}

