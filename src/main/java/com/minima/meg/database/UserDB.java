package com.minima.meg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;
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
		
		//Prepared statements
		SQL_SELECT_ALLUSERS = mSQLConnection.prepareStatement("SELECT * FROM users");
	}

	public JSONArray getAllUsers() throws SQLException {
		
		//Run the query
		ResultSet rs = SQL_SELECT_ALLUSERS.executeQuery();
		
		JSONArray results = new JSONArray();
		while(rs.next()) {
			
			String username = rs.getString("username");
			String level 	= rs.getString("level");
			long timemilli	= rs.getLong("created");
			
			JSONObject res = new JSONObject();
			res.put("username", username);
			res.put("level", level);
			res.put("created", timemilli);
			
			results.put(res);
		}
		
		return results;
	}
}
