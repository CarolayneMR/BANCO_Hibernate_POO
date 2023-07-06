package org.exemplo.persistencia.database.db;

import java.sql.Connection;

import org.hibernate.SessionFactory;

public interface IConnection {

	public Connection getConnection();
	
	public SessionFactory getSessionFactory();
	
	public void closeConnection();
}
