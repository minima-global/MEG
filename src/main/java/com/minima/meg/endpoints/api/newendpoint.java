package com.minima.meg.endpoints.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class newendpoint extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return;
		}
		
		String endpoint = request.getParameter("endpoint");
		String command  = request.getParameter("command");
		
		//Add to the database
		MegDB.getDB().getUserDB().addendpoint(endpoint, command);
		
		zOut.println("<center><br><br>Endpoint "+endpoint+" added</center>"); 
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("ADD ENDPOINT", endpoint, usersesh.getString("username"));
	}
	
}
