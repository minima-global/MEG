package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.HTTPClient;
import com.minima.meg.utils.Log;

public class walletapi extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        doAppiCall("GET",request, "user", out);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        doAppiCall("POST",request, "user", out);
	}
	
	public void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut) {
		
		//Check the Auth token is a valid api-caller
		String auth = request.getHeader("Authorization");
		
		//For now is OK
		String apiuser = "apicaller";
		
		//Which Endpoint
		String apicall = request.getRequestURI().substring(8);
		
		Log.log("WALLET API : "+apicall);
		
		JSONObject resp = null;
		String cmdtocall = "";
		if(apicall.equals("create")) {
			//Create a new WALLET..
			cmdtocall = "keys action:genkey";
		
		}else if(apicall.equals("seedphrase")) {
			
			//Get the seed..
			String seed = request.getParameter("seedphrase");
			
			//Create a new WALLET..
			cmdtocall = "keys action:genkey phrase:\""+seed+"\"";
		
		}else if(apicall.equals("balance")) {
			
			//Get the address..
			String address = request.getParameter("address");
			
			//Create a new WALLET..
			cmdtocall = "balance megammr:true address:"+address;
		
		}else if(apicall.equals("send")) {
			
			
		}
		
		//Now run the command
		try {
			String res 			= HTTPClient.runMinimaCMD(cmdtocall);
			JSONObject fullresp = new JSONObject(res);
			resp 				= fullresp.getJSONObject("response"); 
			resp.put("status", true);
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL", apicall, apiuser);
			
		} catch (Exception e) {
			resp = new JSONObject();
			resp.put("status", false);
			resp.put("error", e.toString());
		}
		
		zOut.println(resp.toString());
	}
}

