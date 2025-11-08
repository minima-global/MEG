package com.minima.meg.database;

import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.minima.meg.utils.SqlDB;

public class LogsDB extends SqlDB {
	
	public LogsDB() {
		super();
	}
	
	@Override
	protected void createSQL() throws SQLException {
		//Create the various tables..
		Statement stmt = mSQLConnection.createStatement();
		
		//Create main table
		String create = "CREATE TABLE IF NOT EXISTS `logs` ("
						+ "  `id` bigint auto_increment,"
						+ "  `event` varchar(256) NOT NULL,"
						+ "  `details` varchar(65536) NOT NULL,"
						+ "  `username` varchar(256) NOT NULL,"
						+ "  `created` bigint NOT NULL"
						+ ")";
		
		//Run it..
		stmt.execute(create);
		
		//Close connection
		stmt.close();
	}


	public JSONObject addLog(String zEvent, String zDetails, String zUser) {
		String sql = "INSERT INTO logs(event, details, username, created) VALUES "
				+ "('"+zEvent+"','"+zDetails+"','"+zUser+"',"+System.currentTimeMillis()+")";
		
		return executeGenericSQL(sql);
	}
	
	public JSONObject getLogs(int zSize, int zOffset){
		return executeGenericSQL("SELECT * FROM logs ORDER BY ID DESC LIMIT "+zSize+" OFFSET "+zOffset);
	}
	
}
