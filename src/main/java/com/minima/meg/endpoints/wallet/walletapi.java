package com.minima.meg.endpoints.wallet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.ApiCaller;
import com.minima.meg.utils.HTTPClientUtil;

public class walletapi extends ApiCaller {

	public void doAppiCall(String zType, HttpServletRequest request, String zUser, PrintWriter zOut) {
		
		//Which Endpoint
		String apicall = request.getRequestURI().substring(8);
		
		//Now run the command
		try {
			
			//What command do we need to call
			String cmdtocall = "";
			
			if(apicall.equals("create")) {
				//Create a new WALLET..
				cmdtocall = "keys action:genkey";
			
			}else if(apicall.equals("seedphrase")) {
				
				//Get the seed..
				String seed = HTTPClientUtil.getValidParam(request,"seedphrase");
				
				//Create a new WALLET..
				cmdtocall = "keys action:genkey phrase:\""+seed+"\"";
			
			}else if(apicall.equals("balance")) {
				
				//Get the address..
				String address = HTTPClientUtil.getValidParam(request,"address");
				
				//Create a new WALLET..
				cmdtocall = "balance megammr:true address:"+address;
			
			}else if(apicall.equals("send")) {
				
				//Get all the parameters
				String tokenid="0x00";
				if(request.getParameter("tokenid") != null) {
					tokenid=request.getParameter("tokenid");
				}
				
				String amount 		= HTTPClientUtil.getValidParam(request, "amount");
				String toaddress 	= HTTPClientUtil.getValidParam(request, "toaddress");
				String fromaddress 	= HTTPClientUtil.getValidParam(request, "fromaddress");
				String privatekey 	= HTTPClientUtil.getValidParam(request, "privatekey");
				String script 		= HTTPClientUtil.getValidParam(request, "script");
				String keyuses 		= HTTPClientUtil.getValidParam(request, "keyuses");
				
				//Create the call
				cmdtocall = "sendfrom"
							+" mine:true"
							+" fromaddress:"+fromaddress
							+" address:"+toaddress
							+" amount:"+amount
							+" tokenid:"+tokenid
							+" script:\""+script+"\""
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
			
			
			}else if(apicall.equals("checktxpow")) {
				
				//Which transaction
				String txpowid 	= HTTPClientUtil.getValidParam(request, "txpowid");
				
				//Create the call
				cmdtocall = "txpow onchain:"+txpowid;
			}
		
			//Run it..
			String res = HTTPClientUtil.runMinimaCMD(cmdtocall);
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL", apicall, zUser);
		
			//And output
			zOut.println(res);
			
		} catch (Exception e) {
			HTTPClientUtil.writeJSONError(zOut,e.toString());
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL FAIL "+e, apicall, zUser);
		}
	}
}

