package com.minima.meg.endpoints.node;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.HTTPClientUtil;
import com.minima.meg.utils.Log;

public class setnode extends BasicPage {
	
	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		JSONObject usersesh = UserSessions.getUserFromSession(request.getSession().getId());
		if(!checkAdminUser(request,zOut)) {
			return;
		}
		
		//Is this a check connection
		if(request.getParameter("checkonly") != null) {
			
		}else {
			
			//First set the Values..
			String host 	= request.getParameter("hostip").trim();
			String meghost	= request.getParameter("megip").trim();
			if(!meghost.endsWith("/")) {
				meghost=meghost+"/";
			}
			
			//Add to the database
			MegDB.getDB().getPrefsDB().setMinimaNode(host);
			MegDB.getDB().getPrefsDB().setMEGNode(meghost);
			
			//Set the WEBHOOK
			try {
				String cmd = "webhooks action:add hook:"+meghost+"webhook";
				String res = HTTPClientUtil.runMinimaCMD(cmd);
				
				JSONObject hook = new JSONObject(res);
				if(!hook.getBoolean("status")) {
					throw new Exception("Failed to set webhook");
				}
				
			} catch (Exception e) {
				Log.log(e+" "+host);
				
				zOut.println("<center><br><br>Minima node set ERROR</center>");
				
				//Add a DB LOG
				MegDB.getDB().getLogsDB().addLog("ERROR SET MINIMA NODE WEBHOOK", host, usersesh.getString("username"));
				
				return;
			}
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("SET MINIMA NODE", host, usersesh.getString("username"));
			
			zOut.println("<center><br><br>Minima node host set : "+host+"</center>"); 
		}
		
		//Test a function..
		String rsp = "Could not connect to to Minima node..";
		try {
			rsp = HTTPClientUtil.runMinimaCMD("block");
		} catch (Exception e) {
			//e.printStackTrace();
			Log.log("Error Minima CMD : "+e.toString());
		}
		
		zOut.println("<center><br><br><div style='word-break:break-all;width:500;'>"+rsp+"</div></center>");
	}
}
