package com.minima.meg.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonDB {

	/**
	 * Simple parameter JSON
	 */
	protected JSONObject mParams;
	
	public JsonDB() {
		mParams = new JSONObject();
	}
	
	public JSONObject getAllData() {
		return mParams;
	}

	public boolean exists(String zName) {
		return mParams.has(zName);
	}
	
	/**
	 * Boolean functions
	 */
	public boolean getBoolean(String zName, boolean zDefault) {
		if(exists(zName)) {
			return mParams.getBoolean(zName);
		}
		
		return zDefault;
	}
	
	public void setBoolean(String zName, boolean zData) {
		mParams.put(zName, zData);
	}
	
	public String getString(String zName, String zDefault) {
		if(exists(zName)) {
			return mParams.getString(zName);
		}
		
		return zDefault;
	}
	
	public void setString(String zName, String zData) {
		mParams.put(zName, zData);
	}
	
	/**
	 * JSONObject
	 */
	public void setJSON(String zName, JSONObject zJSON) {
		mParams.put(zName, zJSON);
	}
	
	public JSONObject getJSON(String zName, JSONObject zDefault) {
		if(exists(zName)) {
			return mParams.getJSONObject(zName);
		}
		
		return zDefault;
	}
	
	/**
	 * JSONArray
	 */
	public void setJSONArray(String zName, JSONArray zJSONArray) {
		mParams.put(zName, zJSONArray);
	}
	
	public JSONArray getJSONArray(String zName, JSONArray zDefault) {
		if(exists(zName)) {
			return mParams.getJSONArray(zName);
		}
		
		return zDefault;
	}
	
	/**
	 * Load JSON DB
	 * @throws IOException 
	 */
	public void loadDB(File zFile) throws IOException {
		
		if(!zFile.exists()) {
			Log.log("Create new PrefDB");
			mParams = new JSONObject();
			return;
		}
		
		FileInputStream fis = new FileInputStream(zFile);
		
		byte[] data = FileUtils.readAllBytes(fis);
		String dbstring = new String(data, Charset.forName("UTF-8"));
		
		mParams = new JSONObject(dbstring);
		
		fis.close();
	}
	
	public void saveDB(File zFile) throws IOException {
		String dbfile = mParams.toString();
		byte[] data = dbfile.getBytes(Charset.forName("UTF-8"));
		FileUtils.writeDataToFile(zFile, data);
	}
	
}
