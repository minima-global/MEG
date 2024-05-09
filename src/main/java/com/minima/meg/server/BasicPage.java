package com.minima.meg.server;

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
import com.minima.meg.utils.Log;

public abstract class BasicPage extends HttpServlet {

	protected void doGet(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("GET "+request.getRequestURI());
		}
	
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
		//Check we are logged in Correctly!
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		
		if(usersesh == null) {
			printSessionExpired(out);
			
		}else {
			String level = usersesh.getString("level");
		
			if(!level.equals("basic") && !level.equals("admin")) {
				printSessionExpired(out);
				
			}else {
				header.writeHeader(level,out);
				writePage(request,out);
			    footer.writeFooter(out);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("POST "+request.getRequestURI());
		}
	
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        
		//Check we are logged in Correctly!
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(usersesh == null) {
			printSessionExpired(out);
		}else {
			String level = usersesh.getString("level");
			header.writeHeader(level,out);
			writePage(request,out);
		    footer.writeFooter(out);
		}
	}
	
	public boolean checkAdminUser(HttpServletRequest zRequest, PrintWriter zOut) {
		HttpSession session = zRequest.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return false;
		}
		return true;
	}
	
	public void printSessionExpired(PrintWriter zOut) {
		zOut.println("<html>"
				+ "<head>"
				+ "<link rel=\"stylesheet\" href=\"style.css\">"
				+ "</head>"
				+ "<body>"
				+ "<center><br><br>");
		zOut.println("Session Expired<br><br>");
		zOut.println("<button class=solobutton onclick='location.href=\"index.html\"'>Back to Login</button>");
		zOut.println("</body></html>");
	}
	
	public abstract void writePage(HttpServletRequest request, PrintWriter zOut);
}
