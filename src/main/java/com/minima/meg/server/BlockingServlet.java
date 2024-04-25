package com.minima.meg.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minima.meg.Log;

public abstract class BlockingServlet extends HttpServlet {
	
	private static Random mRand = new Random();
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
		      									throws ServletException, IOException {
		
		JSONObject params = new JSONObject();
		
		//Any GET params..
		if(request.getQueryString() != null) {
			StringTokenizer strtok = new StringTokenizer(request.getQueryString(),"&");
			while(strtok.hasMoreElements()) {
				String fullparam = strtok.nextToken();
				
				int equals = fullparam.indexOf("=");
				if(equals != -1) {
					
					String key 		= fullparam.substring(0,equals);
					String value 	= fullparam.substring(equals+1,fullparam.length());
					
					//GET requests always add all params as Strings..
					params.put(key, value);
				}
			}
		}
		
		//Now run it..
		JSONObject res = getResponse(request.getRequestURI(), params);
			
		String resp = res.toString();
		if(Log.LOGGING_ENABLED) {
			Log.log("GET "+request.getRequestURI()+" "+params.toString()+" REPLY:"+resp);
		}
		
		//Always reply in JSON
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(resp);
    }
	
	protected void doPost(	HttpServletRequest request,
							HttpServletResponse response)
				throws ServletException, IOException {
		
		//Get the POST params
		BufferedReader br = request.getReader();
				
		String tot = "";
		String line = br.readLine();
		while(line != null) {
			tot = tot+line;
			line = br.readLine();
		}
		
		//Now make a JSON
		JSONObject json = new JSONObject(tot);

		//Now run it..
		JSONObject res = getResponse(request.getRequestURI(), json);
		
		String resp = res.toString();
		if(Log.LOGGING_ENABLED) {
			Log.log("POST "+request.getRequestURI()+" "+json.toString()+" REPLY:"+resp);
		}
		
		//Always reply in JSON
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().println(resp);
	}
	
	protected abstract JSONObject getResponse(String zPath, JSONObject zParams);
}
