package com.minima.meg.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minima.meg.Log;
import com.minima.meg.utils.FileUtils;

public class basic extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
				throws ServletException, IOException {
		
		
		if(Log.LOGGING_ENABLED) {
			Log.log("INDEX GET "+request.getRequestURI());
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
