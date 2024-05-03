package com.minima.meg.endpoints.node;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.HTTPClient;

public class setnode extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return;
		}
		
		String host = request.getParameter("hostip");
		
		//Add to the database
		MegDB.getDB().getPrefsDB().setMinimaNode(host);
		
		zOut.println("<center><br><br>Minima node host set : "+host+"</center>"); 
		
		//Test a function..
		String rsp = "Error..";
		try {
			rsp = HTTPClient.runMinimaCMD("block");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		zOut.println("<center><br><br><div style='word-break:break-all;width:500;'>"+rsp+"</div></center>");

		//Add a DB LOG
		MegDB.getDB().getLogsDB().addLog("SET MINIMA NODE", host, usersesh.getString("username"));
	}
}
