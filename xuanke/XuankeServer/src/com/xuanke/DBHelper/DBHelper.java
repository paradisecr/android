package com.xuanke.DBHelper;



import java.sql.*;;

public class DBHelper {
	private static final String url = "jdbc:mysql://localhost:3306/sunyao";
	private static final String user = "root";
	private static final String pass = "123456";
	
	 
	public DBHelper(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  Connection getConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
}
