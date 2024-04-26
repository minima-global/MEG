package com.minima.meg.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.minima.meg.utils.FileUtils;
import com.minima.meg.utils.Log;

public class DefaultLoader extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("RESOURCE GET "+request.getRequestURI()+" session:"+session.getId());
		}
		
		String reqfile = request.getRequestURI();
		if(reqfile.endsWith("/")) {
			reqfile += "index.html";
		}
		
		if(reqfile.startsWith("/")) {
			reqfile = reqfile.substring(1);
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
