package com.minima.meg.objects;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MiniFormat {

	/*public static JSONArray convertToJSON(String zJsonString) throws ParseException {
		
		//Convert response..
		return (JSONArray) new JSONParser().parse(zJsonString);
	}
	
	public static String JSONPretty(JSONArray zJSONArray) {
		return JSONPretty(zJSONArray, false);
	}*/
	
	/*public static String JSONPretty(JSONArray zJSONArray, boolean zDebug) {
		Iterator<JSONObject> it = zJSONArray.iterator();
		
		boolean arr = false;
		if(zJSONArray.size()>1) {
			arr = true;
		}
		
		String result = "";
		if(arr) {
			result = "[";
		}
		
		boolean first =true;
		while(it.hasNext()) {
			
			if(!first) {
				result += ",";
			}
			first = false;
			
			JSONObject json = it.next();
			String res = JSONPretty(json,zDebug);
			
			result += res;
		}
		
		if(arr) {
			result += "]";
		}
		
		return result;
	}
	
	public static String JSONPretty(JSONObject zJSONObj) {
		return JSONPretty(zJSONObj, false);
	}
	
	public static String JSONPretty(JSONObject zJSONObj, boolean zDebug) {
		//The Work String
		String work = zJSONObj.toString().trim();
		
		//Too long clogs it up.. 
		int len = work.length();
		
		if(zDebug) {
			MinimaLogger.log("JSONPRETTY len:"+len+" obj:"+zJSONObj.toString());
		}
		
		//Check length ?
//		if(len>100000) {
//			//TOO LONG...
//			return work;	
//		}
		
		//The Copy
		String ret       = "";
		
		try {
			
			int tabs         = 0;
			String tabstring = maketabstring(tabs);
			int oldpos       = 0;
			int currentpos   = 0;
			
			while(true) {
				oldpos = currentpos;
				
				int indquotes = work.indexOf("\"",currentpos);
				int indopen   = work.indexOf("{",currentpos);
				int indclose  = work.indexOf("}",currentpos);
				int indcomma  = work.indexOf(",",currentpos);
				
				if(indquotes == -1 && indopen == -1 && indclose == -1 && indcomma == -1) {
					//Add the rest..
					ret += work.substring(currentpos, len);
					
					//Finished..
					break;
				}
				
				if(indquotes == -1) {indquotes = Integer.MAX_VALUE;}
				if(indopen == -1)   {indopen   = Integer.MAX_VALUE;}
				if(indclose == -1)  {indclose  = Integer.MAX_VALUE;}
				if(indcomma == -1)  {indcomma  = Integer.MAX_VALUE;}
				
				if(indopen < indclose && indopen < indcomma && indopen < indquotes) {
					//OPEN BRACKET
					tabs++;
					tabstring = maketabstring(tabs);
					
					String substr = work.substring(oldpos,indopen);
					currentpos = indopen+1;
					ret += substr;
					
					ret += "{\n";
					ret += tabstring;
				}else  if(indclose < indopen && indclose < indcomma && indclose < indquotes) {
					//CLOSE BRACKET
					tabs--;
					tabstring = maketabstring(tabs);
					
					String substr = work.substring(oldpos,indclose);
					currentpos = indclose+1;
					ret += substr;
					
					ret += "\n";
					ret += tabstring;
					ret += "}";
				
				}else  if(indquotes < indopen && indquotes < indcomma && indquotes < indclose) {
					//Get the inbetween bit..
					String prequote = work.substring(oldpos,indquotes);
					ret += prequote;
					
					//Quoted string..
					int quoteend = work.indexOf("\"",indquotes+1);
					
					//Get the text
					String quote = work.substring(indquotes,quoteend+1);
					
					currentpos = quoteend+1;
					ret += quote;
					
				}else{
					//COMMA
					String substr = work.substring(oldpos,indcomma);
					currentpos = indcomma+1;
					ret += substr;
					
					ret+= ",\n";
					ret += tabstring;
				}
			}
			
			//Clean up the response
			ret = ret.replaceAll("\\\\/", "/");
			ret = ret.replaceAll("\\\\n", "\n");
			
		}catch(Exception exc) {
			if(zDebug) {
				MinimaLogger.log("[!] Error JSONPretty.. return unmodified : "+work,false);
			}
			return work;
		}
		
		return ret;
	}*/
	
	private static String maketabstring(int zNum) {
		String ret = "";
		for(int i=0;i<zNum;i++) {
			ret+="  ";
		}
		return ret;
	}
	
	public static String formatSize(long v) {
	    if (v < 1024) return v + " bytes";
	    int z = (63 - Long.numberOfLeadingZeros(v)) / 10;
	    return String.format("%.1f %sB", (double)v / (1L << (z*10)), " KMGTPE".charAt(z));
	}
	
	public static String zeroPad(int zTotLength, MiniNumber zNumber) {
		//Get the number..
		String num       = zNumber.floor().toString();
		int len          = num.length();
		int add 		 = zTotLength-len;
		for(int i=0;i<add;i++) {
			num = "0"+num;
		}
		
		return num;
	}
	
	public static String ConvertMilliToTime(long zMilli) {
		
		long milliseconds = zMilli;
		
		long dy = TimeUnit.MILLISECONDS.toDays(milliseconds);
		
		long yr = dy / 365;
		dy %= 365;
		
		long mn = dy / 30;
		dy %= 30;
		
		long wk = dy / 7;
		dy %= 7;
		
		long hr = TimeUnit.MILLISECONDS.toHours(milliseconds)
				- TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(milliseconds));
		
		long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
				- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliseconds));
		
		long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
				- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds));
		
		long ms = TimeUnit.MILLISECONDS.toMillis(milliseconds)
				- TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(milliseconds));
		
		return String.format("%d Years %d Months %d Weeks %d Days %d Hours %d Minutes %d Seconds", 
						yr,mn, wk, dy, hr, min, sec);
	}

	public static String createRandomString(int len) {
		Random rand = new Random();
		byte[] data = new byte[len];
		rand.nextBytes(data);
		BigInteger bignum = new BigInteger(1,data);
		return bignum.toString(32).toUpperCase();
	}
	
}
