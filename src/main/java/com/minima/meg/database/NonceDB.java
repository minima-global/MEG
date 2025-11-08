package com.minima.meg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.minima.meg.utils.Log;
import com.minima.meg.utils.SqlDB;

public class NonceDB extends SqlDB {
	
	PreparedStatement NONCEDB_START_KEYUSES 	= null;
	PreparedStatement NONCEDB_SET_KEYUSES 		= null;
	PreparedStatement NONCEDB_SETALL_KEYUSES 	= null;
	PreparedStatement NONCEDB_GET_KEYUSES 		= null;
	PreparedStatement NONCEDB_GET_MAXKEYUSES 	= null;
	
	public NonceDB() {
		super();
	}
	
	@Override
	protected void createSQL() throws SQLException {
		//Create the various tables..
		Statement stmt = mSQLConnection.createStatement();
		
		//Create main table
		String create = "CREATE TABLE IF NOT EXISTS `nonce` ("
						+ "  `id` bigint auto_increment,"
						+ "  `publickey` varchar(256) NOT NULL,"
						+ "  `keyuses` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
		
		//Close connection
		stmt.close();
		
		NONCEDB_START_KEYUSES 	= mSQLConnection.prepareStatement("INSERT INTO nonce(publickey,keyuses) VALUES (?,?)");
		NONCEDB_GET_KEYUSES 	= mSQLConnection.prepareStatement("SELECT keyuses FROM nonce WHERE publickey=?");
		NONCEDB_SET_KEYUSES 	= mSQLConnection.prepareStatement("UPDATE nonce SET keyuses=? WHERE publickey=?");
		NONCEDB_SETALL_KEYUSES 	= mSQLConnection.prepareStatement("UPDATE nonce SET keyuses=? WHERE publickey != '0xDDEEAADD'");
		NONCEDB_GET_MAXKEYUSES	= mSQLConnection.prepareStatement("SELECT MAX(keyuses) as maxkeys FROM nonce");
	}


	public int getStartKeyUses(String zPublickey) {
		
		try {
			NONCEDB_GET_KEYUSES.clearParameters();
			NONCEDB_GET_KEYUSES.setString(1, zPublickey);
			
			//Run the query
			ResultSet rs = NONCEDB_GET_KEYUSES.executeQuery();
			
			//Is there a valid result.. ?
			if(rs.next()) {
				
				//Get the details..
				int keyuses = rs.getInt("keyuses");
				
				return keyuses;
				
			}else{
				int y=0;
				//Add to db
				startKeyUses(zPublickey,MegDB.MINIMUM_KEYUSES);
			}
			
			//Not found yet - must be first time
			return MegDB.MINIMUM_KEYUSES;
			
		} catch (SQLException e) {
			Log.log(e.toString());
		}
		
		return -1;
	}
	
	public int getMaxKeyUses() {
		
		try {
			//Run the query
			ResultSet rs = NONCEDB_GET_MAXKEYUSES.executeQuery();
			
			//Is there a valid result.. ?
			if(rs.next()) {
				
				//Get the details..
				int keyuses = rs.getInt("maxkeys");
				
				return keyuses;
			}
			
			//Not found yet - must be first time
			return 0;
			
		} catch (SQLException e) {
			Log.log(e.toString());
		}
		
		return -1;
	}
	
	public void startKeyUses(String zPublickey, int zKeyuses) {
		
		try {
			NONCEDB_START_KEYUSES.clearParameters();
			
			NONCEDB_START_KEYUSES.setString(1, zPublickey);
			NONCEDB_START_KEYUSES.setInt(2, zKeyuses);
			
			//Run the query
			NONCEDB_START_KEYUSES.execute();
			
		} catch (SQLException e) {
			Log.log(e.toString());
		}
	}

	public void setKeyUses(String zPublickey, int zKeyuses) {
		
		try {
			NONCEDB_SET_KEYUSES.clearParameters();
			
			NONCEDB_SET_KEYUSES.setInt(1, zKeyuses);
			NONCEDB_SET_KEYUSES.setString(2, zPublickey);
			
			//Run the query
			NONCEDB_SET_KEYUSES.execute();
			
		} catch (SQLException e) {
			Log.log(e.toString());
		}
	}
	
	public void setAllKeyUses(int zKeyuses) {
		
		try {
			NONCEDB_SETALL_KEYUSES.clearParameters();
			NONCEDB_SETALL_KEYUSES.setInt(1, zKeyuses);
			
			//Run the query
			NONCEDB_SET_KEYUSES.execute();
			
		} catch (SQLException e) {
			Log.log(e.toString());
		}
	}
	
	public int getAndIncrementKeyUses(String zPublickey) throws Exception {
		
		//Get current uses..
		int keyuses = getStartKeyUses(zPublickey);
		
		//Some error has Occurred..
		if(keyuses == -1) {
			throw new Exception("Database fail..");
		}
			
		//Is is less then the min..
		if(keyuses < MegDB.MINIMUM_KEYUSES) {
			keyuses = MegDB.MINIMUM_KEYUSES;
		}
		
		//Now increment
		setKeyUses(zPublickey, keyuses+1);
		
		return keyuses;
	}
}
