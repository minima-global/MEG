package com.minima.meg.endpoints;

import java.io.PrintWriter;

import com.minima.meg.server.BasicPage;

public class home extends BasicPage {

	@Override
	public void writePage(PrintWriter zOut) {
		zOut.println("<h1>Home Page</h1>");
	}
}
