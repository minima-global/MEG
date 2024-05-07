package com.minima.meg.endpoints.profile;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class updatepassword extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		int userid 			= usersesh.getInt("userid");
		
		String newpassword = request.getParameter("newpassword");
		
		//Add to the database
		MegDB.getDB().getUserDB().updatePassword(userid, newpassword);
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("SET MINIMA NODE", "***", usersesh.getString("username"));
			
		zOut.println("<center><br><br>Password updated for : "+usersesh.getString("username")+"</center>"); 
	}
}
