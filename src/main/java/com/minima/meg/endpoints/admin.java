package com.minima.meg.endpoints;

import java.io.PrintWriter;

import com.minima.meg.server.BasicPage;

public class admin extends BasicPage {

	@Override
	public void writePage(PrintWriter zOut) {
		
		
		zOut.println("<h1>ADMIN</h1>");
		zOut.println("<br><br>");
		
	}
}
