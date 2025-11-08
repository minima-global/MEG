package com.minima.meg.endpoints.trigger;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class newtrigger extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		JSONObject usersesh = UserSessions.getUserFromSession(request.getSession().getId());
		if(!checkAdminUser(request,zOut)) {
			return;
		}
		
		String trigger 		= request.getParameter("trigger").trim();
		String extradata  	= request.getParameter("extradata").trim();
		String url  		= request.getParameter("url").trim();
		
		//Add to the database
		MegDB.getDB().getUserDB().addTrigger(trigger, extradata, url);
		
		zOut.println("<center><br><br>Trigger "+trigger+" added</center>"); 
		
		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("ADD TRIGGER", trigger, usersesh.getString("username"));
	}
	
}
