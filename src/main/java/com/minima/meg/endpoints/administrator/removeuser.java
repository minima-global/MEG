package com.minima.meg.endpoints.administrator;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class removeuser extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return;
		}
		
		//Delete this user
		int userid = Integer.parseInt(request.getParameter("userid"));
		
		//Add to the database
		MegDB.getDB().getUserDB().removeUser(userid);
		
		zOut.println("<center><br><br>User removed</center>");
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("REMOVE USER", userid+"", usersesh.getString("username"));
	}
}
