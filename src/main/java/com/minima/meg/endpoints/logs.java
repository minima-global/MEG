package com.minima.meg.endpoints;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;

public class logs extends BasicPage{

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		String offset = request.getParameter("offset");
		if(offset == null) {
			offset = "0";
		}
		
		//How many logs to get in a go
		int lognum = 10;
		
		//Show logs..
		JSONObject users = MegDB.getDB().getLogsDB().getLogs(lognum, Integer.parseInt(offset));
		
		zOut.println("<h2>LOGS</h2>");
		zOut.println("<center>");
		zOut.println("<table border=0 style=\"width:100%;border-spacing:4;\">"
				+ "		<tr>"
				+ "			<td><b>ID</b></td>"
				+ "			<td width=100%><b>Event</b></td>"
				+ "			<td><b>Details</b></td>"
				+ "			<td><b>User</b></td>"
				+ "			<td><b>Created</b></td>"
				+ "		</tr>");
		
		zOut.println("<tr><td colspan=5><hr></td></tr>");
		
		//Now output the rows
		int rows = users.getInt("count");
		for(int i=0;i<rows;i++) {
			JSONObject row = users.getJSONArray("rows").getJSONObject(i);
			
			zOut.println("<tr>");
			zOut.println("<td nowrap>"+row.getString("ID")+"</td>");
			zOut.println("<td nowrap>"+row.getString("EVENT")+"</td>");
			zOut.println("<td nowrap>"+getValueCheckLength(row,"DETAILS")+"</td>");
			zOut.println("<td nowrap>"+row.getString("USERNAME")+"</td>");
			zOut.println("<td nowrap>"+new Date(row.getLong("CREATED"))+"</td>");
			zOut.println("</tr>");
			
			zOut.println("<tr><td colspan=5><hr></td></tr>");
		}
		zOut.println("</table>");
		
		zOut.println("<br>");
		zOut.println("<script type=\"text/javascript\">\n"
				+ "	var offset		="+offset+";\n"
				+ "	var offsetchange="+lognum+";\n"
				+ "	\n"
				+ "	function nextPage(){\n"
				+ "		var newoffset=offset+offsetchange;\n"
				+ "		location.href='logs.html?offset='+newoffset;\n"
				+ "	}\n"
				+ "	\n"
				+ "	function prevPage(){\n"
				+ "		var newoffset=offset-offsetchange;\n"
				+ "		if(newoffset<0){\n"
				+ "			newoffset=0;\n"
				+ "		}\n"
				+ "		location.href='logs.html?offset='+newoffset;\n"
				+ "	}\n"
				+ "	\n"
				+ "</script>\n"
				+ "	\n"
				+ "<button class='solobutton' onclick=\"prevPage();\">PREVIOUS</button>&nbsp;&nbsp;\n"
				+ "<button class='solobutton' onclick=\"nextPage();\">NEXT</button>\n"
				+ "");
		
		zOut.println("</center>");
	}

	public String getValueCheckLength(JSONObject zJSON, String zValue) {
		String value = zJSON.getString(zValue);
		if(value.length() > 40) {
			value = value.substring(0,40)+"..";
		}
		return value;
	}
}
