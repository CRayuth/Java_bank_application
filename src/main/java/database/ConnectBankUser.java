package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ConnectBankUser {
	private static final Logger logger = Logger.getLogger(ConnectBankUser.class.getName());
	
	// Mock mode - set to true to disable database connection
	private static final boolean MOCK_MODE = true;
	
	// Database configuration (for when database is enabled)
	private static final String URL = "jdbc:mysql://localhost:3306/bankdata";
	private static final String USER = "root";
	private static final String PASSWORD = "Udom@12345$";
	
	public static Connection getConnection(){
		if (MOCK_MODE) {
			logger.info("Running in MOCK MODE - Database disabled");
			return null; // Return null when in mock mode
		}
		
		try {
			// Load MySQL JDBC driver explicitly
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASSWORD);
		}
		catch (ClassNotFoundException e) {
			logger.severe("MySQL JDBC Driver not found: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
		catch (SQLException e) {
			logger.severe("Database connection failed: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	
	// Method to check if running in mock mode
	public static boolean isMockMode() {
		return MOCK_MODE;
	}
	
	// Method to enable/disable mock mode (for future use)
	public static void setMockMode(boolean mockMode) {
		// This can be implemented later when you want to toggle modes
		logger.info("Mock mode setting: " + mockMode);
	}
}

