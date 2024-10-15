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
		return getString("minima_node","").trim();
	}

	public void setMEGNode(String zHost) {
		setString("meg_node", zHost);
	}
	
	public String getMEGNode() {
		return getString("meg_node","").trim();
	}
	
	public boolean hasMinimaRPCPassword() {
		if(getMinimaRPCPassword().equals("")) {
			return false;
		}
		return true;
	}
	
	public void setMinimaRPCPassword(String zPassword) {
		setString("rpc_password", zPassword);
	}
	
	public String getMinimaRPCPassword() {
		return getString("rpc_password","").trim();
	}
}
