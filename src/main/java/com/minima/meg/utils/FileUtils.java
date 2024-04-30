package com.minima.meg.utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils {

	public static byte[] loadResouceFile(Object zFromClass, String zResource) throws IOException {
		
		//Get the Resource file
		InputStream is 	= zFromClass.getClass().getClassLoader().getResourceAsStream(zResource);
		if(is == null) {
			return null;
		}
		
		//Get all the data..
		byte[] file = readAllBytes(is);
		is.close();
		
		return file;
	}
	
	public static byte[] readAllBytes(InputStream inputStream) throws IOException {
	    final int bufLen 	= 1024;
	    byte[] buf 			= new byte[bufLen];
	    int readLen;
	    
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        while ((readLen = inputStream.read(buf, 0, bufLen)) != -1) {
        	outputStream.write(buf, 0, readLen);
        }
            
        return outputStream.toByteArray();
	}
	
	public static void writeDataToFile(File zFile, byte[] zData) throws IOException {
		//Check Parent
		File parent = zFile.getAbsoluteFile().getParentFile();
		if(!parent.exists()) {
			parent.mkdirs();
		}
		
		//Delete the old..
		if(zFile.exists()) {
			zFile.delete();
			zFile.createNewFile();
		}else {
			zFile.createNewFile();
		}
		
		//Write it out..
		FileOutputStream fos = new FileOutputStream(zFile, false);
		DataOutputStream fdos = new DataOutputStream(fos);
		
		//And write it..
		fdos.write(zData);
		
		//flush
		fdos.flush();
		fos.flush();
		
		fdos.close();
		fos.close();
	}
	
	public static String getContentType(String zFile) {
		
		String ending;
		int dot = zFile.lastIndexOf(".");
		if(dot != -1) {
			ending = zFile.substring(dot+1);
		}else {
			return "text/plain";
		}
		
		if(ending.equals("html")) {
			return "text/html";
		}else if(ending.equals("htm")) {
			return "text/html";
		}else if(ending.equals("css")) {
			return "text/css";
		}else if(ending.equals("js")) {
			return "text/javascript";
		}else if(ending.equals("txt")) {
			return "text/plain";
		}else if(ending.equals("xml")) {
			return "text/xml";
		
		}else if(ending.equals("jpg")) {
			return "image/jpeg";
		}else if(ending.equals("jpeg")) {
			return "image/jpeg";
		}else if(ending.equals("png")) {
			return "image/png";
		}else if(ending.equals("gif")) {
			return "image/gif";
		}else if(ending.equals("svg")) {
			return "image/svg+xml";
		}else if(ending.equals("ico")) {
			return "image/ico";
		
		}else if(ending.equals("ttf")) {
			return "font/ttf";
		
		}else if(ending.equals("zip")) {
			return "application/zip";
		}else if(ending.equals("pdf")) {
			return "application/pdf";
		}else if(ending.equals("wasm")) {
			return "application/wasm";
			
		}else if(ending.equals("mp3")) {
			return "audio/mp3";
		}else if(ending.equals("wav")) {
			return "audio/wav";
		}
		
		return "text/plain";
	}
}
