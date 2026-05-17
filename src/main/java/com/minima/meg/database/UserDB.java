package com.minima.meg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.minima.meg.utils.SqlDB;

public class UserDB extends SqlDB {

	PreparedStatement SQL_SELECT_ALLUSERS 	= null;
	PreparedStatement SQL_GET_USER 			= null;
	PreparedStatement SQL_INSERT_USER 		= null;
	PreparedStatement SQL_DELETE_USERNAME	= null;
	PreparedStatement SQL_DELETE_USERID		= null;
	PreparedStatement SQL_UPDATE_USER		= null;
	
	PreparedStatement SQL_SELECT_ALLAPIENDPOINTS 	= null;
	PreparedStatement SQL_SELECT_APIENDPOINT 		= null;
	PreparedStatement SQL_CREATE_APIENDPOINT 		= null;
	PreparedStatement SQL_DELETE_APIENDPOINT 		= null;
	
	PreparedStatement SQL_SELECT_ALLTRIGGERS 	= null;
	PreparedStatement SQL_SELECT_TRIGGER 		= null;
	PreparedStatement SQL_INSERT_TRIGGER 		= null;
	PreparedStatement SQL_DELETE_TRIGGER 		= null;
	
	public UserDB() {
		super();
	}
	
	@Override
	protected void createSQL() throws SQLException {
		//Create the various tables..
		Statement stmt = mSQLConnection.createStatement();
		
		//Create main table
		String create = "CREATE TABLE IF NOT EXISTS `users` ("
						+ "  `id` bigint auto_increment,"
						+ "  `username` varchar(256) NOT NULL,"
						+ "  `password` varchar(256) NOT NULL,"
						+ "  `level` varchar(256) NOT NULL,"
						+ "  `created` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
		
		//Create api endpoint table
		create 		= "CREATE TABLE IF NOT EXISTS `apiendpoints` ("
						+ "  `id` bigint auto_increment,"
						+ "  `endpoint` varchar(256) NOT NULL,"
						+ "  `command` varchar(4096) NOT NULL,"
						+ "  `created` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
		
		//Create trigger table
		create 		= "CREATE TABLE IF NOT EXISTS `triggers` ("
						+ "  `id` bigint auto_increment,"
						+ "  `trigger` varchar(256) NOT NULL,"
						+ "  `extradata` varchar(256) NOT NULL,"
						+ "  `url` varchar(1024) NOT NULL,"
						+ "  `created` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
		
		//Close connection
		stmt.close();
		
		SQL_SELECT_ALLUSERS = mSQLConnection.prepareStatement("SELECT * FROM users");
		SQL_GET_USER		= mSQLConnection.prepareStatement("SELECT * FROM users WHERE username=? and password=?");
		SQL_INSERT_USER		= mSQLConnection.prepareStatement("INSERT INTO users(username,password,level,created) VALUES (?,?,?,?)");
		SQL_DELETE_USERNAME	= mSQLConnection.prepareStatement("DELETE FROM users WHERE username=?");
		SQL_DELETE_USERID	= mSQLConnection.prepareStatement("DELETE FROM users WHERE id=?");
		SQL_UPDATE_USER		= mSQLConnection.prepareStatement("UPDATE users SET password=? WHERE id=?");
		
		SQL_SELECT_ALLAPIENDPOINTS 	= mSQLConnection.prepareStatement("SELECT * FROM apiendpoints");
		SQL_SELECT_APIENDPOINT		= mSQLConnection.prepareStatement("SELECT * FROM apiendpoints WHERE endpoint=?");
		SQL_CREATE_APIENDPOINT		= mSQLConnection.prepareStatement("INSERT INTO apiendpoints(endpoint, command, created) VALUES (?,?,?)");
		SQL_DELETE_APIENDPOINT		= mSQLConnection.prepareStatement("DELETE FROM apiendpoints WHERE id=?");
	
		SQL_SELECT_ALLTRIGGERS	= mSQLConnection.prepareStatement("SELECT * FROM triggers");
		SQL_SELECT_TRIGGER		= mSQLConnection.prepareStatement("SELECT * FROM triggers WHERE trigger=?");
		SQL_INSERT_TRIGGER		= mSQLConnection.prepareStatement("INSERT INTO triggers(trigger, extradata, url, created) VALUES (?,?,?,?)");
		SQL_DELETE_TRIGGER		= mSQLConnection.prepareStatement("DELETE FROM triggers WHERE id=?");
	}
	
	/**
	 * USER FUNCTIONS
	 */
	public JSONObject getAllUsers(){
		
		try {
			//Run the query
			ResultSet rs = SQL_SELECT_ALLUSERS.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//OLD
		//return executeGenericSQL("SELECT * FROM users");
	}
	
	public JSONObject getUser(String zUsername, String zPassword){
	
		try {
			
			//Set Params
			SQL_GET_USER.clearParameters();
			SQL_GET_USER.setString(1, zUsername);
			SQL_GET_USER.setString(2, zPassword);
			
			//Run the query
			ResultSet rs = SQL_GET_USER.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//OLD
//		return executeGenericSQL("SELECT * FROM users "
//				+ "WHERE username='"+zUsername+"' AND password='"+zPassword+"'");
	}
	
	public JSONObject addUser(String zUsername, String zPassword, String zLevel) {
		
		try {
			
			//Set Params
			SQL_INSERT_USER.clearParameters();
			SQL_INSERT_USER.setString(1, zUsername);
			SQL_INSERT_USER.setString(2, zPassword);
			SQL_INSERT_USER.setString(3, zLevel);
			SQL_INSERT_USER.setLong(4, System.currentTimeMillis());
			
			//Run the query
			SQL_INSERT_USER.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//OLD
//		String sql = "INSERT INTO users(username,password,level,created) VALUES "
//				+ "('"+zUsername+"','"+zPassword+"','"+zLevel+"',"+System.currentTimeMillis()+")";
//		JSONObject res = executeGenericSQL(sql);
//		Log.log("INISERT : "+res.toString());
//		return executeGenericSQL(sql);
	}
	
	public JSONObject removeUser(String zUsername) {
		
		try {
			
			//Set Params
			SQL_DELETE_USERNAME.clearParameters();
			SQL_DELETE_USERNAME.setString(1, zUsername);
			
			//Run the query
			SQL_DELETE_USERNAME.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}

		//return executeGenericSQL("DELETE FROM users WHERE username='"+zUsername+"'");
	}
	
	public JSONObject removeUser(int zUserID) {
		
		try {
			
			//Set Params
			SQL_DELETE_USERID.clearParameters();
			SQL_DELETE_USERID.setInt(1, zUserID);
			
			//Run the query
			SQL_DELETE_USERID.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("DELETE FROM users WHERE id="+zUserID);
	}
	
	public JSONObject updatePassword(int zUserID, String zPassword) {
		
		try {
			
			//Set Params
			SQL_UPDATE_USER.clearParameters();
			SQL_UPDATE_USER.setString(1, zPassword);
			SQL_UPDATE_USER.setInt(2, zUserID);
			
			//Run the query
			SQL_UPDATE_USER.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//String sql = "UPDATE users SET password='"+zPassword+"' WHERE id="+zUserID;
		//return executeGenericSQL(sql);
	}
	
	/**
	 * API ENDPOINTS
	 */
	public JSONObject getAllEndpoionts(){
		try {
			//Run the query
			ResultSet rs = SQL_SELECT_ALLAPIENDPOINTS.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("SELECT * FROM apiendpoints");
	}
	
	public JSONObject getEndpoiont(String zEndpoint){
		
		try {
			
			//Set Params
			SQL_SELECT_APIENDPOINT.clearParameters();
			SQL_SELECT_APIENDPOINT.setString(1, zEndpoint);
			
			//Run the query
			ResultSet rs = SQL_SELECT_APIENDPOINT.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		
		//return executeGenericSQL("SELECT * FROM apiendpoints WHERE endpoint='"+zEndpoint+"'");
	}
	
	public JSONObject addEndpoint(String zEndpoint, String zCommand) {

		try {
			
			//Set Params
			SQL_CREATE_APIENDPOINT.clearParameters();
			SQL_CREATE_APIENDPOINT.setString(1, zEndpoint);
			SQL_CREATE_APIENDPOINT.setString(2, zCommand);
			SQL_CREATE_APIENDPOINT.setLong(3, System.currentTimeMillis());
			
			//Run the query
			SQL_CREATE_APIENDPOINT.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		
		//String sql = "INSERT INTO apiendpoints(endpoint, command, created) VALUES "
		//		+ "('"+zEndpoint+"','"+zCommand+"',"+System.currentTimeMillis()+")";
		//return executeGenericSQL(sql);
	}
	
	public JSONObject removeEndpoint(int zEndpointID) {
		
		try {
			
			//Set Params
			SQL_DELETE_APIENDPOINT.clearParameters();
			SQL_DELETE_APIENDPOINT.setInt(1, zEndpointID);
			
			//Run the query
			SQL_DELETE_APIENDPOINT.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("DELETE FROM apiendpoints WHERE id="+zEndpointID);
	}
	
	/**
	 * TRIGGERS
	 */
	public JSONObject getAllTriggers(){
		
		try {
			//Run the query
			ResultSet rs = SQL_SELECT_ALLTRIGGERS.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("SELECT * FROM triggers");
	}
	
	public JSONObject getTrigger(String zTrigger){
		
		try {
			
			//Set Params
			SQL_SELECT_TRIGGER.clearParameters();
			SQL_SELECT_TRIGGER.setString(1, zTrigger);
			
			//Run the query
			ResultSet rs = SQL_SELECT_TRIGGER.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("SELECT * FROM triggers WHERE trigger='"+zTrigger+"'");
	}
	
	public JSONObject addTrigger(String zTrigger, String zExtraData, String zURL) {
		
		try {
			
			//Set Params
			SQL_INSERT_TRIGGER.clearParameters();
			SQL_INSERT_TRIGGER.setString(1, zTrigger);
			SQL_INSERT_TRIGGER.setString(2, zExtraData);
			SQL_INSERT_TRIGGER.setString(3, zURL);
			SQL_INSERT_TRIGGER.setLong(4, System.currentTimeMillis());
			
			//Run the query
			SQL_INSERT_TRIGGER.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//String sql = "INSERT INTO triggers(trigger, extradata, url, created) VALUES "
	//			+ "('"+zTrigger+"','"+zExtraData+"','"+zURL+"',"+System.currentTimeMillis()+")";
		//return executeGenericSQL(sql);
	}
	
	public JSONObject removeTrigger(int zTriggerID) {
		
		try {
			
			//Set Params
			SQL_DELETE_TRIGGER.clearParameters();
			SQL_DELETE_TRIGGER.setInt(1, zTriggerID);
			
			//Run the query
			SQL_DELETE_TRIGGER.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("DELETE FROM triggers WHERE id="+zTriggerID);
	}
	
}
