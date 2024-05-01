package com.minima.meg.utils;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

public class HTTPClient {

	
	public static void POST(String zURL, String zData) throws Exception {
		
		//Log.log("POST REQ:"+zURL+" DATA:"+zData);
		
		HttpClient client = new HttpClient();
        client.start();
        
        Request request = client.POST(zURL);
        request.header(HttpHeader.CONTENT_TYPE, "application/json");
        
        /*// Add basic auth header if credentials provided
        if (isCredsAvailable()) {
            String authString = username + ":" + password;
            byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
            String authStringEnc = "Basic " + new String(authEncBytes);
            request.header(HttpHeader.AUTHORIZATION, authStringEnc);
        }*/
        
        request.content(new StringContentProvider(zData), "application/json");
        request.send();
        
        client.stop();
	}
}
