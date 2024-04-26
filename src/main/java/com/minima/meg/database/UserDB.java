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
	}

	public JSONObject getAllUsers(){
		return executeGenericSQL("SELECT * FROM users");
	}
}
