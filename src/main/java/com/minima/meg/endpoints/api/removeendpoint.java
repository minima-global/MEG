package com.minima.meg.endpoints.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class removeendpoint extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		JSONObject usersesh = UserSessions.getUserFromSession(request.getSession().getId());
		if(!checkAdminUser(request,zOut)) {
			return;
		}
		
		//Delete this endpoint
		int endid = Integer.parseInt(request.getParameter("endid"));
		
		MegDB.getDB().getUserDB().removeEndpoint(endid);
		
		zOut.println("<center><br><br>Endpoint removed</center>");
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("REMOVE ENDPOINT", endid+"", usersesh.getString("username"));
	}
}
