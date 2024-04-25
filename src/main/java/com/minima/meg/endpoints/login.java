package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.minima.meg.Log;
import com.minima.meg.server.UserSessions;

public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("LOGIN GET "+request.getRequestURI()+" session:"+session.getId());
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Add to Session..
		UserSessions.addSession(session.getId(), username);
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
		PrintWriter out = response.getWriter();
	    out.println("<html>");
	    out.println("<body>");
	    out.println("Login!");
	    out.println("<br>");
	    out.println(username+" "+password+"<br><br>");
	    out.println("<a href='home.html'>HOME</a>");
	    out.println("</body>");
	    out.println("</html>");
	}
	
}
