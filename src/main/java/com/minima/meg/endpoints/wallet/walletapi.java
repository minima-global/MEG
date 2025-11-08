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
		
		//Is this a cache call
		boolean use_cache = false;
		
		//Now run the command
		try {
			
			//What command do we need to call
			String cmdtocall = "";
			String cmdtocallnoprivate = null;
			
			if(apicall.equals("create")) {
				
				//Create a new WALLET..
				cmdtocall = "keys action:genkey";
			
			}else if(apicall.equals("seedphrase")) {
				
				use_cache = true;
				
				//Get the seed..
				String seed = HTTPClientUtil.getValidParam(request,"seedphrase");
				
				//Create a new WALLET..
				cmdtocall = "keys action:genkey phrase:\""+seed+"\"";
			
				//A no private key message
				cmdtocallnoprivate = "keys action:genkey phrase:***";
				
			}else if(apicall.equals("balance")) {
				
				use_cache = true;
				
				//Get the address..
				String address = HTTPClientUtil.getValidParam(request,"address");
				
				//Create a new WALLET..
				cmdtocall = "balance megammr:true address:"+address;
			
			}else if(apicall.equals("checkaddress")) {
				
				use_cache = true;
				
				//Get the address..
				String address = HTTPClientUtil.getValidParam(request,"address");
				
				//Check if this is a valid address
				cmdtocall = "checkaddress address:"+address;
			
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
				String burn 		= HTTPClientUtil.getValidParam(request, "burn","0");
				String mine 		= HTTPClientUtil.getValidParam(request, "mine","true");
				
				//Get the key uses - could be specified or MEG DB
				String keyuses = getKeyUses(request);
				
				//Create the call
				cmdtocall = "sendfrom"
							+" mine:"+mine
							+" fromaddress:"+fromaddress
							+" address:"+toaddress
							+" amount:"+amount
							+" burn:"+burn
							+" tokenid:"+tokenid
							+" script:\""+script+"\""
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
				
				cmdtocallnoprivate = "sendfrom"
						+" mine:"+mine
						+" fromaddress:"+fromaddress
						+" address:"+toaddress
						+" amount:"+amount
						+" burn:"+burn
						+" tokenid:"+tokenid
						+" script:\""+script+"\""
						+" privatekey:***"
						+" keyuses:"+keyuses;
			
				//Shall we add STATE ? Only added in later versions of Minima
				if(HTTPClientUtil.paramExists(request, "state")) {
					String state = HTTPClientUtil.getValidParam(request, "state");
					
					cmdtocall 			+= " state:"+state;
					cmdtocallnoprivate 	+= " state:"+state;
				}
				
			}else if(apicall.equals("consolidate")) {
				
				//Get all the parameters
				String tokenid="0x00";
				if(request.getParameter("tokenid") != null) {
					tokenid=request.getParameter("tokenid");
				}
				
				String maxcoins 	= HTTPClientUtil.getValidParam(request, "maxcoins","50");
				String fromaddress 	= HTTPClientUtil.getValidParam(request, "fromaddress");
				String privatekey 	= HTTPClientUtil.getValidParam(request, "privatekey");
				String script 		= HTTPClientUtil.getValidParam(request, "script");
				String burn 		= HTTPClientUtil.getValidParam(request, "burn","0");
				String mine 		= HTTPClientUtil.getValidParam(request, "mine","true");
				
				//Get the key uses - could be specified or MEG DB
				String keyuses = getKeyUses(request);
				
				//Create the call
				cmdtocall = "consolidatefrom"
							+" mine:"+mine
							+" maxcoins:"+maxcoins
							+" fromaddress:"+fromaddress
							+" burn:"+burn
							+" tokenid:"+tokenid
							+" script:\""+script+"\""
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
				
				cmdtocallnoprivate = "consolidatefrom"
						+" mine:"+mine
						+" maxcoins:"+maxcoins
						+" fromaddress:"+fromaddress
						+" burn:"+burn
						+" tokenid:"+tokenid
						+" script:\""+script+"\""
						+" privatekey:***"
						+" keyuses:"+keyuses;
			
			}else if(apicall.equals("checktxpow")) {
				
				use_cache = true;
				
				//Which transaction
				String txpowid 	= HTTPClientUtil.getValidParam(request, "txpowid");
				
				//Create the call
				cmdtocall = "txpow onchain:"+txpowid;
			
			}else if(apicall.equals("block")) {
				
				use_cache = true;
				
				//Create the call
				cmdtocall = "block";
					
			}else if(apicall.equals("random")) {
				
				//Create the call
				cmdtocall = "random";
				
			}else if(apicall.equals("gettxpow")) {
				
				use_cache = true;
				
				if(HTTPClientUtil.paramExists(request, "txpowid")) {
					
					//Which transaction
					String txpowid 	= HTTPClientUtil.getValidParam(request, "txpowid");
					
					//Create the call
					cmdtocall = "txpow txpowid:"+txpowid;
				
				}else if(HTTPClientUtil.paramExists(request, "block")) {
					
					//Which transaction
					String block = HTTPClientUtil.getValidParam(request, "block");
					
					//Create the call
					cmdtocall = "txpow block:"+block;
					
				}else {
					throw new Exception("MUST provide txpowid or block param");
				}
				
			}else if(apicall.equals("scanchain")) {
				
				use_cache = true;
				
				//What depth to scan
				String depth = HTTPClientUtil.getValidParam(request, "depth", "16");
				
				//Create the call
				cmdtocall = "scanchain depth:"+depth;
			
				//Only add param if added to call.. so does not break OLDer Minima
				cmdtocall = checkAddParam(request, "offset", "offset", cmdtocall);
				
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
				
				//Get the key uses - could be specified or MEG DB
				String keyuses = getKeyUses(request);
				
				//Create the call
				cmdtocall = "signfrom"
							+" data:"+data
							+" privatekey:"+privatekey
							+" keyuses:"+keyuses;
				
				cmdtocallnoprivate = "signfrom"
						+" data:"+data
						+" privatekey:***"
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
				
				use_cache = true;
				
				//Get all the parameters
				String address = HTTPClientUtil.getValidParam(request, "address");
				String tokenid = HTTPClientUtil.getValidParam(request, "tokenid","0x00");
				
				//List all coins..
				if(tokenid.equals("0x01")) {
					cmdtocall = "coins address:"+address+" megammr:true";
				}else {
					cmdtocall = "coins address:"+address+" megammr:true tokenid:"+tokenid;
				}
				
				//Only add param if added to call.. so does not break OLDer Minima
				cmdtocall = checkAddParam(request, "state", "state", cmdtocall);
				
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
				
			
			}else if(apicall.equals("rawtxn")) {
				
				String inputs   = HTTPClientUtil.getValidParam(request, "inputs");
				String outputs  = HTTPClientUtil.getValidParam(request, "outputs");
				String scripts  = HTTPClientUtil.getValidParam(request, "scripts");
				String state    = HTTPClientUtil.getValidParam(request, "state", "{}");
				
				cmdtocall 		= "rawtxnfrom inputs:"+inputs+" outputs:"+outputs+" scripts:"+scripts+" state:"+state;
				
			
			}else if(apicall.equals("viewtxn")) {
				
				use_cache = true;
				
				String txndata = HTTPClientUtil.getValidParam(request, "data");
				
				cmdtocall = "txnview data:"+txndata;
				
			}else {
				throw new Exception("Unknown WALLET API call : "+apicall);
			}
			
			//Is this a cache call..
			String res = null;
			if(use_cache && MegDB.CACHE_DB_ENABLED) {
				
				//Is there an answer
				String cacheresp = MegDB.getDB().getCacheDB().getCacheData(cmdtocall);
				if(cacheresp != null) {
					
					//Set the cached response
					res = cacheresp;
				
				}else {
					
					if(cmdtocallnoprivate == null) {
						Log.debug("Minima > "+cmdtocall);
					}else {
						Log.debug("Minima > "+cmdtocallnoprivate);
					}
					
					//No Cache entry
					res = HTTPClientUtil.runMinimaCMD(cmdtocall, false);
					
					//And ADD it..
					MegDB.getDB().getCacheDB().addCacheCall(cmdtocall, res);
				}
			
			}else {
				
				if(cmdtocallnoprivate == null) {
					Log.debug("Minima > "+cmdtocall);
				}else {
					Log.debug("Minima > "+cmdtocallnoprivate);
				}
				
				//Run it..
				res = HTTPClientUtil.runMinimaCMD(cmdtocall, false);
			}
			
			//Add a DB LOG
			if(cmdtocallnoprivate == null) {
				MegDB.getDB().getLogsDB().addLog("WALLET CALL", apicall+"> "+cmdtocall, zUser);
			}else {
				MegDB.getDB().getLogsDB().addLog("WALLET CALL", apicall+"> "+cmdtocallnoprivate, zUser);
			}
			
			//And output
			zOut.println(res);
			
		} catch (Exception e) {
			HTTPClientUtil.writeJSONError(zOut,e.toString());
			
			//Add a DB LOG
			MegDB.getDB().getLogsDB().addLog("WALLET CALL FAIL "+e, apicall, zUser);
		}
	}
	
	public String getKeyUses(HttpServletRequest request) throws Exception {
		
		//Have they specified a key uses..
		String keyuses = "";
		
		if(HTTPClientUtil.paramExists(request, "keyuses")) {
			
			//Get the key uses specified..
			keyuses = HTTPClientUtil.getValidParam(request, "keyuses");
			
			//Is the Public key specified.. so we can update the DB
			if(HTTPClientUtil.paramExists(request, "publickey")) {
				
				String pubkey = HTTPClientUtil.getValidParam(request, "publickey");
				
				//Create if not exist and set
				MegDB.getDB().getNonceDB().getStartKeyUses(pubkey);
				MegDB.getDB().getNonceDB().setKeyUses(pubkey, Integer.parseInt(keyuses)+1);
			}
			
		}else if(HTTPClientUtil.paramExists(request, "publickey")) {
			
			//Get the DB Public KEY
			String pubkey = HTTPClientUtil.getValidParam(request, "publickey");
			
			//Use the MEG value..
			keyuses = ""+MegDB.getDB().getNonceDB().getAndIncrementKeyUses(pubkey);
		
		}else {
			throw new Exception("MUST provide either keyuses or publickey param for DB lookup..");
		}
		
		return keyuses;
	}
	
	//Use this to add params ONLY if they are present.. as they have been added in later version of Minima only.
	public String checkAddParam(HttpServletRequest request, String zParamName, String  zAddParam, String zOldCommand) throws Exception {
		
		if(HTTPClientUtil.paramExists(request, zParamName)) {
			String param = HTTPClientUtil.getValidParam(request, zParamName);
			if(!param.equals("")) {
				return zOldCommand+" "+zAddParam+":"+param;
			}
		}
		
		return zOldCommand;
	}
}

