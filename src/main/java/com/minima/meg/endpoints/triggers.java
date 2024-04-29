package com.minima.meg.endpoints;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.server.BasicPage;

public class triggers extends BasicPage {

	@Override
	public void writePage(HttpServletRequest request, PrintWriter zOut) {
		zOut.println("<h2>TRIGGERS</h2>");
	}
}
