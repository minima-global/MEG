package com.minima.meg.endpoints;

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

public class login extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		if(Log.LOGGING_ENABLED) {
			Log.log("POST "+request.getRequestURI());
		}
		
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//Check the Username and Password..
		JSONObject user = MegDB.getDB().getUserDB().getUser(username, password);
		
		int count	 = user.getInt("count");
		String level = "";
		int userid  = -1;
		
		//Command line
		if(username.equals("admin") && password.equals("root")) {
			level 		= "admin";
			
		}else if(count==0){
			
			//User Not found..
			response.setContentType("text/html");
	        response.setStatus(HttpServletResponse.SC_OK);
	        
	        out.println("<html><body><center><br><br>");
		    out.println("User / Password NOT Found..<br><br>");
		    out.println("<a href='index.html'>Back to Login</a></center>");
		    out.println("</body></html>");
		    
		    return;
		    
		}else {
			
			//Create a session object
			JSONObject userjson = user.getJSONArray("rows").getJSONObject(0);
			level 				= userjson.getString("LEVEL");
			userid				= userjson.getInt("ID");
		}
		
		JSONObject userobj  = new JSONObject();
		userobj.put("userid", userid);
		userobj.put("username", username);
		userobj.put("level", level);
		
		//Add to Session..
		UserSessions.addSession(session.getId(), userobj);
		
		response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
		header.writeHeader(level,out);
		out.println("<center><br><br>Login Successful!</center>");
	    footer.writeFooter(out);
	    
	    //Add a DB LOG
	    MegDB.getDB().getLogsDB().addLog("LOGIN", "", username);
	}
}
