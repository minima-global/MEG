package com.minima.meg.endpoints.trigger;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class removetrigger extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
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
