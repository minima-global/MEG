package com.minima.meg.database;

import com.minima.meg.utils.JsonDB;

public class PrefsDB extends JsonDB {

	public PrefsDB() {
		super();
	}
	
	public void setMinimaNode(String zHost) {
		setString("minima_node", zHost);
	}
	
	public String getMinimaNode() {
		return getString("minima_node","");
	}

	public void setMEGNode(String zHost) {
		setString("meg_node", zHost);
	}
	
	public String getMEGNode() {
		return getString("meg_node","");
	}
}
