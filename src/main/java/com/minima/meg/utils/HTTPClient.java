package com.minima.meg.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;

import com.minima.meg.database.MegDB;

public class HTTPClient {

	public static void POST(String zURL, String zData) throws Exception {
		
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
	
	public static String GET(String zURL) throws Exception {
		
		HttpClient client = new HttpClient();
        client.start();
        
        ContentResponse res = client.GET(zURL);
        String resp = res.getContentAsString();
        
        client.stop();
        
        return resp;
	}
	
	public static String runMinimaCMD(String zCommand) throws Exception {
		
		//get the Host..
		String mhost = MegDB.getDB().getPrefsDB().getMinimaNode();
		if(!mhost.endsWith("/")) {
			mhost = mhost+"/";
		}
		
		//Url encode the command..
		String cmd = URLEncoder.encode(zCommand,"UTF-8");
		
		//Now run it.. 
		String fullurl = mhost+cmd;
		
		return GET(fullurl);
	}
}
