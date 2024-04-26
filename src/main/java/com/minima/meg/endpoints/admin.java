package com.minima.meg.endpoints;

import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.BasicPage;

public class admin extends BasicPage {

	@Override
	public void writePage(PrintWriter zOut) {
		
		//Get all the users
		JSONObject users = MegDB.getDB().getUserDB().getAllUsers();
		
		zOut.println("<h1>ADMIN</h1>");
		zOut.println("<br><br>");
		zOut.println("USERS : "+users.toString());
		
	}
}
