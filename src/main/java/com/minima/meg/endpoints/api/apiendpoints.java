package com.minima.meg.endpoints.api;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.server.JettyServer;

public class apiendpoints extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>Endpoints</h2>");
		
		
		String url = request.getRequestURL().toString();
		int index  = url.indexOf("apiendpoints.html");
		url = url.substring(0,index);
		
		
		zOut.println("Adding an 'Endpoint' to 'sendmoney' enables "+url+"api/sendmoney<br>");
		zOut.println("<br>'$param' in the 'Command' will be replaced with the GET or POST parameters<br>");
		zOut.println("<br>Enpoints can only be called by 'apicaller' level Users - using Basic Authorization Header<br>");
		zOut.println("<br>For example : <br>");
		zOut.println("<br><code>curl http://127.0.0.1:"+JettyServer.MEG_PORT+"/api/sendtoken -X POST "
					+ "--user apicalleruser:password -d \"address=0xEE&amount=100\"</code><br>");
		
		zOut.println("<h3>Add Endpoint</h3>");
		zOut.println("<form action=\"newendpoint.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Endpoint : </td>\r\n"
				+ "				<td><input size=50 type=text name=endpoint></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Command : </td>\r\n"
				+ "				<td><input size=50 type=text name=command></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input class='solobutton' type=submit value=\"Add New Endpoint\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
		
		//All endpoints
		zOut.println("<h3>All Endpoints</h3>");
		
		JSONObject endpoints = MegDB.getDB().getUserDB().getAllEndpoionts();
		
		zOut.println("<center>"
				+ "<table border=0 style=\"width:100%;border-spacing:4;\">"
				+ "		<tr>"
				+ "			<td><b>ENDPOINT</b></td>"
				+ "			<td><b>COMMAND</b></td>"
				+ "			<td>&nbsp;</td>"
				+ "		</tr>");
		
		zOut.println("<tr><td colspan=3><hr></td></tr>");
		
		//Now output the rows
		int rows = endpoints.getInt("count");
		for(int i=0;i<rows;i++) {
			JSONObject row = endpoints.getJSONArray("rows").getJSONObject(i);
			
			zOut.println("<tr>");
			zOut.println("<td>"+row.getString("ENDPOINT")+"</td>");
			zOut.println("<td><code>"+row.getString("COMMAND")+"</code></td>");
			zOut.println("<td><a class=menu href='removeendpoint.html?endid="+row.getLong("ID")+"'>REMOVE</a></td>");
			zOut.println("</tr>");
			
			zOut.println("<tr><td colspan=3><hr></td></tr>");
		}
		zOut.println("</table></center>");
	
	}
}
