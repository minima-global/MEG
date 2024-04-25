package com.minima.meg.endpoints;

import java.io.PrintWriter;

import com.minima.meg.server.BasicPage;
import com.minima.meg.server.UserSessions;

public class home extends BasicPage {

	@Override
	public void writePage(PrintWriter zOut) {
		
		UserSessions.printAllSessions();
		
		zOut.println("<h1>Home Page</h1>");
		zOut.println("<br><br>");
		zOut.println("<a href='logoff.html'>LOG OFF</a>");
	}
}
