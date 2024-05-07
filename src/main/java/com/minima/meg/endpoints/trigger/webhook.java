package com.minima.meg.endpoints.trigger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minima.meg.utils.messages.Message;

public class webhook extends HttpServlet {
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response)
			throws ServletException, IOException {
	
		
		//Get ALL POST data
		BufferedReader br = request.getReader();
				
		String tot = "";
		String line = br.readLine();
		while(line != null) {
			tot  = tot+line;
			line = br.readLine();
		}
		
		//Now make a JSON
		JSONObject json = new JSONObject(tot);
		
		String event 	= json.getString("event");
		JSONObject data = json.getJSONObject("data");
		
		//Create a message for the Trigger Handler.. separate thread
		Message trigger = new Message(TriggerProcessor.NEW_MINIMA_EVENT);
		trigger.addString("event", event);
		trigger.addObject("data", data);
		
		TriggerProcessor.getTriggerManager().PostMessage(trigger);
		
		//Say all ok..
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        
        JSONObject resp = new JSONObject();
        resp.put("status", true);
        
        out.write(resp.toString());
	}
}
