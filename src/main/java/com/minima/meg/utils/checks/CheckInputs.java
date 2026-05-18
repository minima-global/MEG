package com.minima.meg.utils.checks;

import com.minima.meg.objects.MiniData;
import com.minima.meg.objects.MiniNumber;

public class CheckInputs {

	public static void checkHex(String zHex) throws CheckInputException{
		
		//Check for Mx..
		if(zHex.toLowerCase().startsWith("mx")) {
			String regex = "^[a-zA-Z0-9]*$";
			if(!zHex.matches(regex)) {
				throw new CheckInputException("Invalid Mx.. format");
			}
			return;
		}
		
		//Check normal hex
		try {
			MiniData data = new MiniData(zHex);
		}catch(Exception zExc) {
			throw new CheckInputException(zExc);
		}
	}
	
	public static void checkNumber(String zNumber) throws CheckInputException {
		try {
			MiniNumber number = new MiniNumber(zNumber);
		}catch(Exception zExc) {
			throw new CheckInputException(zExc);
		}
	}
	
	public static void checkBoolean(String zBoolean) throws CheckInputException {
		String lower = zBoolean.toLowerCase().trim();
		if(!lower.equals("true") && !lower.equals("false")) {
			throw new CheckInputException("Invalid boolean value");
		}
	}
	
	public static void checkSeed(String zSeed) throws CheckInputException {
		String regex = "^[a-zA-Z0-9\\!\\?_-[\\s]]*$";
		if(!zSeed.matches(regex)) {
			throw new CheckInputException("Invalid seed value");
		}
	}
	
	public static void checkScript(String zScript) throws CheckInputException {
		if(zScript.contains(";")) {
			throw new CheckInputException("Invalid script");
		}
	}
	
	
	public static void main(String[] zArgs) {
		try {
			checkHex("MxG086KHYJ37R04C6MY36Z6HEAPBSP4E8HGFZ9GB97T2U3TRZSPTV5ESKKH20K6");
			checkHex("0xFF");
			checkHex("FF");
		
			checkNumber("0.1");
			
			checkSeed("asd0x09090! ! ? ! _  -  909   ");
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		
		
	}
}
