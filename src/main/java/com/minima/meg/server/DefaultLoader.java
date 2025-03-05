package com.minima.meg.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.utils.FileUtils;
import com.minima.meg.utils.Log;

public class DefaultLoader extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException, IOException {
		
		//Log.log("GET RESOURCE "+request.getRequestURI());
		
		String reqfile = request.getRequestURI();
		if(reqfile.endsWith("/")) {
			reqfile += "index.html";
		}
		
		if(reqfile.startsWith("/")) {
			reqfile = reqfile.substring(1);
		}
		
		//Is this the login page..
		if(reqfile.equals("index.html")) {
			
			//Are there any users to log in with..
			JSONObject users = MegDB.getDB().getUserDB().getAllUsers();
			if(users.getInt("count") == 0 && !MegDB.getDB().getAdminEnabled()) {
				reqfile = "index_nousers.html";
			}
		}
		
		//Load the file
		byte[] data = FileUtils.loadResouceFile(this, reqfile);
		if(data == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		
		//What is the mime type...
		String mime = FileUtils.getContentType(reqfile);
		
		//Set content type
		response.setContentType(mime);
        
		//Valid response incoming..
		response.setStatus(HttpServletResponse.SC_OK);
        
		//Send the data
        ServletOutputStream sos = response.getOutputStream();
        sos.write(data, 0, data.length);
	}
}
