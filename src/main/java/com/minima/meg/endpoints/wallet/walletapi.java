package com.minima.meg.endpoints.wallet;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import com.minima.meg.database.MegDB;
import com.minima.meg.server.ApiCaller;
import com.minima.meg.utils.HTTPClientUtil;
import com.minima.meg.utils.Log;

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
				String burn 		= HTTPClientUtil.getValidParam(request, "burn","0");
				
				//Create the call
				cmdtocall = "sendfrom"
							+" mine:true"
							+" fromaddress:"+fromaddress
							+" address:"+toaddress
							+" amount:"+amount
							+" burn:"+burn
							+" tokenid:"+tokenid
							+" script:\""+script+"\""
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
			
			
			}else if(apicall.equals("checktxpow")) {
				
				//Which transaction
				String txpowid 	= HTTPClientUtil.getValidParam(request, "txpowid");
				
				//Create the call
				cmdtocall = "txpow onchain:"+txpowid;
			
			}else if(apicall.equals("gettxpow")) {
				
				//Which transaction
				String txpowid 	= HTTPClientUtil.getValidParam(request, "txpowid");
				
				//Create the call
				cmdtocall = "txpow txpowid:"+txpowid;
			
				//Advanced APIs..
			}else if(apicall.equals("unsignedtxn")) {
				
				//Get all the parameters
				String amount 		= HTTPClientUtil.getValidParam(request, "amount");
				String toaddress 	= HTTPClientUtil.getValidParam(request, "toaddress");
				String fromaddress 	= HTTPClientUtil.getValidParam(request, "fromaddress");
				String script 		= HTTPClientUtil.getValidParam(request, "script");
				
				String burn 		= HTTPClientUtil.getValidParam(request, "burn","0");
				String tokenid		= HTTPClientUtil.getValidParam(request, "tokenid","0x00");
				
				//Create the call
				cmdtocall = "createfrom"
							+" fromaddress:"+fromaddress
							+" address:"+toaddress
							+" amount:"+amount
							+" burn:"+burn
							+" tokenid:"+tokenid
							+" script:\""+script+"\"";
				
			}else if(apicall.equals("signtxn")) {
				
				String data 		= HTTPClientUtil.getValidParam(request, "data");
				String privatekey 	= HTTPClientUtil.getValidParam(request, "privatekey");
				String keyuses 		= HTTPClientUtil.getValidParam(request, "keyuses");
				
				//Create the call
				cmdtocall = "signfrom"
							+" data:"+data
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
				
				
			}else if(apicall.equals("posttxn")) {
				
				String data 		= HTTPClientUtil.getValidParam(request, "data");
				
				//Create the call
				cmdtocall = "postfrom data:"+data+" mine:true";
			
			}else if(apicall.equals("lockcoins")) {
				
				//Create the call
				cmdtocall = "txncoinlock action:lock";
			
			}else if(apicall.equals("unlockcoins")) {
				
				//Create the call
				cmdtocall = "txncoinlock action:unlock";
			
			}else if(apicall.equals("minetxn")) {
				
				String data = HTTPClientUtil.getValidParam(request, "data");
				
				//Create the call
				cmdtocall = "txnmine data:"+data;
			
			}else if(apicall.equals("postminedtxn")) {
				
				String data = HTTPClientUtil.getValidParam(request, "data");
				
				//Create the call
				cmdtocall = "txnminepost data:"+data;
			
			
			}else if(apicall.equals("listcoins")) {
				
				//Get all the parameters
				String address = HTTPClientUtil.getValidParam(request, "address");
				String tokenid = HTTPClientUtil.getValidParam(request, "tokenid","0x00");
				
				cmdtocall = "coins address:"+address+" megammr:true tokenid:"+tokenid;
			
			}else if(apicall.equals("constructtxn")) {
				
				//Get all the parameters
				String coinlist		= HTTPClientUtil.getValidParam(request, "coinlist");
				String script		= HTTPClientUtil.getValidParam(request, "script");
				
				String toaddress	= HTTPClientUtil.getValidParam(request, "toaddress");
				String toamount		= HTTPClientUtil.getValidParam(request, "toamount");
				
				String changeaddress	= HTTPClientUtil.getValidParam(request, "changeaddress");
				String changeamount		= HTTPClientUtil.getValidParam(request, "changeamount");
				
				String tokenid		= HTTPClientUtil.getValidParam(request, "tokenid","0x00");
				
				cmdtocall = "constructfrom coinlist:"+coinlist+" script:\""+script+"\""
							+" toaddress:"+toaddress+" toamount:"+toamount
							+" changeaddress:"+changeaddress+" changeamount:"+changeamount+" tokenid:"+tokenid;
				
			}else {
				throw new Exception("Unknown WALLET API call : "+apicall);
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

