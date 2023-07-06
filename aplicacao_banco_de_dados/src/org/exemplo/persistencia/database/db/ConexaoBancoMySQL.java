package org.exemplo.persistencia.database.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.exemplo.persistencia.database.util.ConfigLoader;
import org.hibernate.SessionFactory;

public class ConexaoBancoMySQL implements IConnection{

	private final static String DB_ADDRESS;
	private final static String DB_PORT;
	private final static String DB_SCHEMA;
	private final static String DB_USER;
	private final static String DB_PASSWORD;
	
	static {
		DB_ADDRESS = ConfigLoader.loadConfig().getProperty("DB_ADDRESS");
		DB_PORT = ConfigLoader.loadConfig().getProperty("DB_PORT");
		DB_SCHEMA = ConfigLoader.loadConfig().getProperty("DB_SCHEMA");
		DB_USER = ConfigLoader.loadConfig().getProperty("DB_USER");
		DB_PASSWORD = ConfigLoader.loadConfig().getProperty("DB_PASSWORD");
	}
	
	private Connection connection;
	
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+DB_ADDRESS+":"+DB_PORT+"/"+DB_SCHEMA, DB_USER, DB_PASSWORD);
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public SessionFactory getSessionFactory() {
		// TODO Auto-generated method stub
		return null;
	}
}
