package org.exemplo.persistencia.database.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.exemplo.persistencia.database.model.Cliente;
import org.exemplo.persistencia.database.model.Conta;
import org.exemplo.persistencia.database.model.RegistroTransacao;
import org.exemplo.persistencia.database.util.ConfigLoader;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class ConexaoBancoHibernate implements IConnection{

	private final static String DB_ADDRESS;
	private final static String DB_PORT;
	private final static String DB_SCHEMA;
	private final static String DB_USER;
	private final static String DB_PASSWORD;
	private final static String HIBERNATE_DRIVE_CLASS;
	private final static String HIBERNATE_DIALECT;
	private final static String HIBERNATE_SHOW_SQL;
	
	static {
		DB_ADDRESS = ConfigLoader.loadConfig().getProperty("DB_ADDRESS");
		DB_PORT = ConfigLoader.loadConfig().getProperty("DB_PORT");
		DB_SCHEMA = ConfigLoader.loadConfig().getProperty("DB_SCHEMA");
		DB_USER = ConfigLoader.loadConfig().getProperty("DB_USER");
		DB_PASSWORD = ConfigLoader.loadConfig().getProperty("DB_PASSWORD");
		HIBERNATE_DRIVE_CLASS = ConfigLoader.loadConfig().getProperty("HIBERNATE_DRIVE_CLASS");
		HIBERNATE_DIALECT = ConfigLoader.loadConfig().getProperty("HIBERNATE_DIALECT");
		HIBERNATE_SHOW_SQL = ConfigLoader.loadConfig().getProperty("HIBERNATE_SHOW_SQL");
	}
	
	private org.hibernate.SessionFactory sessionFactory;
	
	private Connection connection;
	
	public org.hibernate.SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
            try {
                // Create Configuration object
                Configuration configuration = new Configuration();

                // Configure database connection properties
                configuration.setProperty("hibernate.connection.url", "jdbc:mysql://"+DB_ADDRESS+":"+DB_PORT+"/"+DB_SCHEMA);
                configuration.setProperty("hibernate.connection.username", DB_USER);
                configuration.setProperty("hibernate.connection.password", DB_PASSWORD);

                // Set dialect
                configuration.setProperty("hibernate.dialect", HIBERNATE_DIALECT);

                // Enable SQL logging (optional)
                configuration.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);

                // Add annotated classes or XML mappings
                configuration.addAnnotatedClass(Cliente.class);
                configuration.addAnnotatedClass(RegistroTransacao.class);
                configuration.addAnnotatedClass(Conta.class);

                // Create StandardServiceRegistryBuilder
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                registryBuilder.applySettings(configuration.getProperties());

                // Build StandardServiceRegistry
                StandardServiceRegistry registry = registryBuilder.build();

                // Build SessionFactory
                sessionFactory = configuration.buildSessionFactory(registry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
	}
	
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
}
