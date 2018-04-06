package com.coetool.servlet.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
	
private static Connection connection = null;

public static Connection getConnection() {
	if (connection != null)
		return connection;
	else {
		// database URL
		
		String dbUrl ="jdbc:postgresql://localhost:2280/CASTCOE";
		try 
		{
		
		Class.forName("org.postgresql.Driver");
		// set the url, username and password for the database
		connection = DriverManager.getConnection(dbUrl, "operator", "CastAIP");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	}
}