package com.minima.meg.endpoints.profile;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class myprofile extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>PROFILE</h2>");
		
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		
		zOut.println("UserID : "+usersesh.getInt("userid"));
		zOut.println("<br>Username : "+usersesh.getString("username"));
		zOut.println("<br>Password : ****");
		zOut.println("<br>");
		
		zOut.println("<h3>Update Password</h3>");
		
		zOut.println("<form action=\"updatepassword.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>New Password : </td>\r\n"
				+ "				<td><input size=50 value='*****' type=password name=newpassword></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input class='solobutton' type=submit value=\"Update\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
	}
}
