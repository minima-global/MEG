package com.minima.meg.endpoints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minima.meg.mainsite.footer;
import com.minima.meg.mainsite.header;

public class home extends base {

	@Override
	public void writePage(PrintWriter zOut) {
		zOut.println("<h1>Home Page</h1>");
	}
}
