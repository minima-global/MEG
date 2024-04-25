package com.minima.meg.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minima.meg.mainsite.footer;
import com.minima.meg.mainsite.header;

public abstract class BasicPage extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		//Check we are logged in Correctly!
		//..
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
		PrintWriter out = response.getWriter();
	    
		header.writeHeader(out);
		
		writePage(out);
	    
	    footer.writeFooter(out);
	}
	
	public abstract void writePage(PrintWriter zOut);
}
