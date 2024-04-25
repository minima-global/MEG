package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minima.meg.Log;

public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		Log.log("Login Attempt.. POST");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
		PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<body>");
	    out.println("Login!");
	    out.println("<br>");
	    out.println(username+" "+password);
	    out.println("</body>");
	    out.println("</html>");
	}
	
}
