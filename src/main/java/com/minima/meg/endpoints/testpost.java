package com.minima.meg.endpoints;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.mainsite.footer;
import com.minima.meg.mainsite.header;
import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.Log;

public class testpost extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
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

		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        
        JSONObject status = new JSONObject();
        status.put("status", true);
        out.println(status.toString());
	}
}
