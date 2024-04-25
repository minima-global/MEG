package com.minima.meg.endpoints;

import org.json.JSONObject;

import com.minima.meg.server.BlockingServlet;

public class basic extends BlockingServlet {
	
	@Override
	protected JSONObject getResponse(JSONObject zParams) {
		return zParams;
	}
	
}
