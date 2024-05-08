package com.minima.meg.utils;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.client.util.StringContentProvider;
import org.eclipse.jetty.http.HttpHeader;
import org.json.JSONObject;

import com.minima.meg.database.MegDB;

public class HTTPClientUtil {

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
	
	public static void writeJSONError(PrintWriter zOut, String zError) {
		JSONObject resp = new JSONObject();
		resp.put("status", false);
		resp.put("error", zError);
		zOut.println(resp.toString());
	}
	
	public static String getValidParam(HttpServletRequest request, String zParam) throws Exception {
		String param = request.getParameter(zParam);
		if(param == null) {
			throw new Exception("Param missing : "+zParam);
		}
		return param;
	}
}
