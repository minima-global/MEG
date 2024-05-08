package com.minima.meg.endpoints.wallet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.ApiCaller;
import com.minima.meg.utils.HTTPClientUtil;
import com.minima.meg.utils.Log;

public class walletapi extends ApiCaller {

	public void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut) {
		
		//Which Endpoint
		String apicall = request.getRequestURI().substring(8);
		
		//Now run the command
		try {
			
			//What command do we need to call
			String cmdtocall = "";
			
			if(apicall.equals("create")) {
				//Create a new WALLET..
				cmdtocall = "keys action:genkey";
			
			}else if(apicall.equals("seedphrase")) {
				
				//Get the seed..
				String seed = HTTPClientUtil.getValidParam(request,"seedphrase");
				
				//Create a new WALLET..
				cmdtocall = "keys action:genkey phrase:\""+seed+"\"";
			
			}else if(apicall.equals("balance")) {
				
				//Get the address..
				String address = HTTPClientUtil.getValidParam(request,"address");
				
				//Create a new WALLET..
				cmdtocall = "balance megammr:true address:"+address;
			
			}else if(apicall.equals("send")) {
				
				//Get all the parameters
				
				
			}
		
			//Run it..
			String res = HTTPClientUtil.runMinimaCMD(cmdtocall);
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL", apicall, zUser);
		
			//And output
			zOut.println(res);
			
		} catch (Exception e) {
			HTTPClientUtil.writeJSONError(zOut,e.toString());
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL FAIL "+e, apicall, zUser);
		}
	}
}

