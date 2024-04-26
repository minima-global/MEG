package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.mainsite.footer;
import com.minima.meg.mainsite.header;
import com.minima.meg.server.UserSessions;
import com.minima.meg.utils.Log;

public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("LOGIN GET "+request.getRequestURI()+" session:"+session.getId());
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Check the Username and Password..
		//..
		
		//Create a session object
		JSONObject userobj = new JSONObject();
		userobj.put("username", username);
		userobj.put("level", "admin");
		
		//Add to Session..
		UserSessions.addSession(session.getId(), userobj);
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
		PrintWriter out = response.getWriter();
	    header.writeHeader(out);
		
		out.println("<center><br><br>Login Successful!</center>");
	    
	    footer.writeFooter(out);
	    
	}
	
}
