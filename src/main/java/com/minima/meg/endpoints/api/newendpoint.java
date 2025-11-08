package com.minima.meg.endpoints.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class newendpoint extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		JSONObject usersesh = UserSessions.getUserFromSession(request.getSession().getId());
		if(!checkAdminUser(request,zOut)) {
			return;
		}
		
		String endpoint = request.getParameter("endpoint").trim();
		String command  = request.getParameter("command").trim();
		
		//Add to the database
		MegDB.getDB().getUserDB().addEndpoint(endpoint, command);
		
		zOut.println("<center><br><br>Endpoint "+endpoint+" added</center>"); 
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("ADD ENDPOINT", endpoint, usersesh.getString("username"));
	}
	
}
