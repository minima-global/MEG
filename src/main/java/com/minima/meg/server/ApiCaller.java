package com.minima.meg.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.utils.HTTPClientUtil;

public abstract class ApiCaller extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        //Check the AUTH Credentials
        String user = "";
        try {
        	user = getAuthUser(request.getHeader("Authorization"));
        }catch(Exception exc) {
        	HTTPClientUtil.writeJSONError(out,exc.toString());
        	return;
        }
        
        doAppiCall("GET",request, user, out);
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
        //Check the AUTH Credentials
        String user = "";
        try {
        	user = getAuthUser(request.getHeader("Authorization"));
        }catch(Exception exc) {
        	HTTPClientUtil.writeJSONError(out,exc.toString());
        	return;
        }
        
        doAppiCall("POST",request, user, out);
	}
	
	public String getAuthUser(String zAuth) throws Exception {
		
		//Check valid
		if(zAuth == null) {
			throw new Exception("No Authorization Header.. ");
		}
		
		//Only BASIC Auth accepted..
		int index 		 = zAuth.indexOf("Basic");
		if(index == -1) {
			throw new Exception("Only Basic Authorization allowed.. ");
		}
		
		String authtoken = zAuth.substring(index+5).trim();
		
		//Base64..
		String unencode = new String(Base64.getDecoder().decode(authtoken));
		
		//Now split..
		String[] userpass = unencode.split(":");
		
		
		//Is the default apicaller account enabled..
		if(userpass[0].equals("apicaller") && MegDB.getDB().getApiCallerEnabled()) {
		
			//Check the password..
			if(!MegDB.getDB().checkApiCallerPassword(userpass[1])) {
				throw new Exception("Invalid User - default apicaller password incorrect");
			}
			
			return "apicaller";
		}
		
		//Now check for this user..
		JSONObject user = MegDB.getDB().getUserDB().getUser(userpass[0], userpass[1]);
		if(user.getInt("count") == 0) {
			throw new Exception("Invalid User - not found");
		}
		
		//Get the user
		JSONObject uu = user.getJSONArray("rows").getJSONObject(0);
		
		//Now check he is an apicaller..
		if(!uu.getString("LEVEL").equals("apicaller")) {
			throw new Exception("Invalid User - not an apicaller");
		}
		
		return uu.getString("USERNAME");
	}
	
	//Process the request..
	public abstract void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut);
}

