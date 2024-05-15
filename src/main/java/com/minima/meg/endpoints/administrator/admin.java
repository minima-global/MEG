package com.minima.meg.endpoints.administrator;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class admin extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Check ADMIN
		HttpSession session = request.getSession();
		JSONObject usersesh = UserSessions.getUserFromSession(session.getId());
		if(!usersesh.getString("level").equals("admin")) {
			zOut.println("<center><br><br>ACCESS DENIED (Not admin user)..</center>");
			return;
		}
		
		zOut.println("<h2>Admin</23>");
		
		//Show all current Users..
		JSONObject users = MegDB.getDB().getUserDB().getAllUsers();
				
		zOut.println("<h3>All Users</h3>");
		zOut.println("<center>"
				+ "<table border=0 style=\"width:100%;border-spacing:4;\">"
				+ "		<tr>"
				+ "			<td><b>Username</b></td>"
				+ "			<td><b>Password</b></td>"
				+ "			<td><b>Level</b></td>"
				+ "			<td><b>Created</b></td>"
				+ "			<td>&nbsp;</td>"
				+ "		</tr>");
		
		zOut.println("<tr><td colspan=5><hr></td></tr>");
		
		//Now output the rows
		int rows = users.getInt("count");
		for(int i=0;i<rows;i++) {
			JSONObject row = users.getJSONArray("rows").getJSONObject(i);
			
			zOut.println("<tr>");
			zOut.println("<td>"+row.getString("USERNAME")+"</td>");
			zOut.println("<td>"+row.getString("PASSWORD")+"</td>");
			zOut.println("<td>"+row.getString("LEVEL")+"</td>");
			zOut.println("<td>"+new Date(row.getLong("CREATED"))+"</td>");
			zOut.println("<td><a class=menu href='removeuser.html?userid="+row.getLong("ID")+"'>REMOVE</a></td>");
			zOut.println("</tr>");
			
			zOut.println("<tr><td colspan=5><hr></td></tr>");
		}
		zOut.println("</table></center>");
		
		//Add new User
		zOut.println("<h3>Add User</h3>");
		zOut.println("<form action=\"newuser.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Username : </td>\r\n"
				+ "				<td><input type=text name=username></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Password : </td>\r\n"
				+ "				<td><input type=password name=password></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Level : </td>\r\n"
				+ "				<td>\r\n"
				+ "					<select name=\"level\">\r\n"
				+ "					  <option value=\"basic\">Basic User</option>\r\n"
				+ "					  <option value=\"admin\">Admin</option>\r\n"
				+ "					  <option value=\"apicaller\">API Caller</option>\r\n"
				+ "					</select>\r\n"
				+ "				</td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input class='solobutton' type=submit value=\"Add New User\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
	}
}
