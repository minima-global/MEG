package com.minima.meg.endpoints;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.server.BasicPage;

public class help extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>WALLET ENDPOINTS</h2>");
		zOut.println("<br>");
		zOut.println("Welcome to the help Section");
	}
}
