package com.minima.meg.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONObject;

import com.minima.meg.utils.SqlDB;

public class LogsDB extends SqlDB {
	
	PreparedStatement SQL_INSERT_LOG 	= null;
	PreparedStatement SQL_SELECT_LOGS 	= null;
	
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
		
		SQL_INSERT_LOG 	= mSQLConnection.prepareStatement("INSERT INTO logs(event, details, username, created) VALUES (?,?,?,?)");
		SQL_SELECT_LOGS	= mSQLConnection.prepareStatement("SELECT * FROM logs ORDER BY ID DESC LIMIT ? OFFSET ?");
	}

	public JSONObject addLog(String zEvent, String zDetails, String zUsername) {
		
		try {
			
			//Set Params
			SQL_INSERT_LOG.clearParameters();
			SQL_INSERT_LOG.setString(1, zEvent);
			SQL_INSERT_LOG.setString(2, zDetails);
			SQL_INSERT_LOG.setString(3, zUsername);
			SQL_INSERT_LOG.setLong(4, System.currentTimeMillis());
			
			//Run the query
			SQL_INSERT_LOG.execute();
			
			return SqlDB.getSQLSuccessUpdateJSON();
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//String sql = "INSERT INTO logs(event, details, username, created) VALUES "
		//		+ "('"+zEvent+"','"+zDetails+"','"+zUser+"',"+System.currentTimeMillis()+")";
		//return executeGenericSQL(sql);
	}
	
	public JSONObject getLogs(int zSize, int zOffset){
		
		try {
			
			//Set Params
			SQL_SELECT_LOGS.clearParameters();
			SQL_SELECT_LOGS.setInt(1, zSize);
			SQL_SELECT_LOGS.setInt(2, zOffset);
			
			//Run the query
			ResultSet rs = SQL_SELECT_LOGS.executeQuery();
			
			return SqlDB.convertResToJSON(rs);
			
		} catch (SQLException e) {
			return SqlDB.getSQLErrorJSON(e);
		}
		
		//return executeGenericSQL("SELECT * FROM logs ORDER BY ID DESC LIMIT "+zSize+" OFFSET "+zOffset);
	}
	
}
