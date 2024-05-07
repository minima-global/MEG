package com.minima.meg.endpoints.trigger;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;

public class triggers extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>TRIGGERS</h2>");
		
		
		//Add new User
		zOut.println("<h3>Add Trigger</h3>");
		zOut.println("<form action=\"newtrigger.html\" method=\"post\">\r\n"
				+ "		<table>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Trigger : </td>\r\n"
				+ "				<td>\r\n"
				+ "					<select name=\"trigger\">\r\n"
				+ "					  <option value=\"NEWBLOCK\">NEWBLOCK</option>\r\n"
				+ "					  <option value=\"NEWBALANCE\">NEWBALANCE</option>\r\n"
				+ "					  <option value=\"NEWTXPOW\">NEWTXPOW</option>\r\n"
				+ "					  <option value=\"ADDRESS_USED\">ADDRESS_USED (use extra data..)</option>\r\n"
				+ "					  <option value=\"TOKEN_USED\">TOKEN_USED (use extra data..)</option>\r\n"
				+ "					  <option value=\"STATEVAR_USED\">STATEVAR_USED (use extra data..)</option>\r\n"
				+ "					</select>\r\n"
				+ "				</td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Eatra Data : </td>\r\n"
				+ "				<td><input size=50 type=text name=extradata></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr><td>&nbsp;</td></tr>"
				+ "			<tr>\r\n"
				+ "				<td class=newuserform>Call URL : </td>\r\n"
				+ "				<td><input size=50 type=text name=url></td>\r\n"
				+ "			</tr>\r\n"
				+ "			<tr>\r\n"
				+ "				<td colspan=2 class=newuserform><input type=submit value=\"Add New Trigger\"></td>\r\n"
				+ "			</tr>\r\n"
				+ "		</table>\r\n"
				+ "		</form>");
		
		//Some info
		zOut.println("On a trigger event a POST request is made with the relevant JSON data to the specified URL<br>");
		zOut.println("You can add multiple triggers for the same event");
		
		//All endpoints
		zOut.println("<h3>All Triggers</h3>");
		
		JSONObject endpoints = MegDB.getDB().getUserDB().getAllTriggers();
		
		zOut.println("<center>"
				+ "<table border=0 style=\"width:100%;border-spacing:4;\">"
				+ "		<tr>"
				+ "			<td><b>TRIGGER</b></td>"
				+ "			<td><b>EXTRADATA</b></td>"
				+ "			<td><b>URL</b></td>"
				+ "			<td>&nbsp;</td>"
				+ "		</tr>");
		
		//Now output the rows
		int rows = endpoints.getInt("count");
		for(int i=0;i<rows;i++) {
			JSONObject row = endpoints.getJSONArray("rows").getJSONObject(i);
			
			zOut.println("<tr>");
			zOut.println("<td>"+row.getString("TRIGGER")+"</td>");
			zOut.println("<td>"+row.getString("EXTRADATA")+"</td>");
			zOut.println("<td>"+row.getString("URL")+"</td>");
			zOut.println("<td><a class=menu href='removetrigger.html?triggerid="+row.getLong("ID")+"'>REMOVE</a></td>");
			zOut.println("</tr>");
		}
		zOut.println("</table></center>");
				
	}
}
