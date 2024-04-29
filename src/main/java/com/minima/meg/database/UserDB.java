package com.minima.meg.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.minima.meg.utils.SqlDB;

public class UserDB extends SqlDB {

	PreparedStatement SQL_SELECT_ALLUSERS = null;
	
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
						+ "  `command` varchar(256) NOT NULL,"
						+ "  `created` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
	}
	
	/**
	 * USER FUNCTIONS
	 */
	public JSONObject getAllUsers(){
		return executeGenericSQL("SELECT * FROM users");
	}
	
	public JSONObject getUser(String zUsername, String zPassword){
		return executeGenericSQL("SELECT * FROM users "
				+ "WHERE username='"+zUsername+"' AND password='"+zPassword+"'");
	}
	
	public JSONObject addUser(String zUsername, String zPassword, String zLevel) {
		String sql = "INSERT INTO users(username,password,level,created) VALUES "
				+ "('"+zUsername+"','"+zPassword+"','"+zLevel+"',"+System.currentTimeMillis()+")";
		return executeGenericSQL(sql);
	}
	
	public JSONObject removeUser(String zUsername) {
		return executeGenericSQL("DELETE FROM users WHERE username='"+zUsername+"'");
	}
	
	public JSONObject removeUser(int zUserID) {
		return executeGenericSQL("DELETE FROM users WHERE id="+zUserID);
	}
	
	/**
	 * API ENDPOINTS
	 */
	public JSONObject getAllEndpoionts(){
		return executeGenericSQL("SELECT * FROM apiendpoints");
	}
	
	public JSONObject getEndpoiont(String zEndpoint){
		return executeGenericSQL("SELECT * FROM apiendpoints WHERE endpoint='"+zEndpoint+"'");
	}
	
	public JSONObject addendpoint(String zEndpoint, String zCommand) {
		String sql = "INSERT INTO apiendpoints(endpoint, command, created) VALUES "
				+ "('"+zEndpoint+"','"+zCommand+"',"+System.currentTimeMillis()+")";
		return executeGenericSQL(sql);
	}
	
	public JSONObject removeEndpoint(int zEndpointID) {
		return executeGenericSQL("DELETE FROM apiendpoints WHERE id="+zEndpointID);
	}
	
}
