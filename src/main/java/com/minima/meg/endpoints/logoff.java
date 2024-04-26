package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.Log;

public class logoff extends HttpServlet {
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("GET "+request.getRequestURI());
		}
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        request.getSession().invalidate();
        
        //Remove the session
      	UserSessions.clearSession(session.getId());
        
		PrintWriter out = response.getWriter();
		
		out.println("<html><body><center><br><br>");
	    out.println("Log Off Success<br><br>");
	    out.println("<a href='index.html'>Back to Login</a></center>");
	    out.println("</body></html>");
	}
	
}
