package com.minima.meg.endpoints;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class newuser extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String level 	= request.getParameter("level");
		
		//Add to the database
		MegDB.getDB().getUserDB().addUser(username, password, level);
		
		zOut.println("<center><br><br>User "+username+" added</center>"); 
	}
	
}
