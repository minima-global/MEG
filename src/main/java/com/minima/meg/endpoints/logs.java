package com.minima.meg.endpoints;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;
import com.minima.meg.utils.Log;

public class logs extends BasicPage{

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		
		//Show logs..
		JSONObject users = MegDB.getDB().getLogsDB().getLogs(10, 0);
		
		zOut.println("<h3>LOGS</h3>");
		zOut.println("<table border=0 style=\"border-spacing:4;\">"
				+ "		<tr>"
				+ "			<td><b>Event</b></td>"
				+ "			<td><b>Details</b></td>"
				+ "			<td><b>User</b></td>"
				+ "			<td><b>Created</b></td>"
				+ "		</tr>");
		
		//Now output the rows
		int rows = users.getInt("count");
		for(int i=0;i<rows;i++) {
			JSONObject row = users.getJSONArray("rows").getJSONObject(i);
			
			zOut.println("<tr>");
			zOut.println("<td>"+row.getString("EVENT")+"</td>");
			zOut.println("<td>"+row.getString("DETAILS")+"</td>");
			zOut.println("<td>"+row.getString("USERNAME")+"</td>");
			zOut.println("<td>"+new Date(row.getLong("CREATED"))+"</td>");
			zOut.println("</tr>");
		}
		zOut.println("</table>");
		
		
	}

}
