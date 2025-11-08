package com.minima.meg.endpoints.trigger;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class removetrigger extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		JSONObject usersesh = UserSessions.getUserFromSession(request.getSession().getId());
		if(!checkAdminUser(request,zOut)) {
			return;
		}
		
		//Delete this endpoint
		int endid = Integer.parseInt(request.getParameter("triggerid"));
		
		MegDB.getDB().getUserDB().removeTrigger(endid);
		
		zOut.println("<center><br><br>Trigger removed</center>");
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("REMOVE TRIGGER", endid+"", usersesh.getString("username"));
	}
}
