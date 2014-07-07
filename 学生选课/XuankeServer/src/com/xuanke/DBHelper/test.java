package com.xuanke.DBHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class test {
	public static void mai(String args[]){
		 String result = "";
		 Connection conn = null;
		 try {
			Class.forName("com.mysql.jdbc.Driver");
			 
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sunyao", "root","123456");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		  //DBHelper db = new DBHelper();
		 // Connection conn = db.getConnection();
		  if(conn == null){
			  System.out.println("数据库连接失败");
			  return;
		  }
		  try {
			PreparedStatement pstmt = conn.prepareStatement("select * from student where Sno=? and Spass=?");
			  pstmt.setString(1, "0121110340419");
			  pstmt.setString(2,"123456");
			  ResultSet rs = pstmt.executeQuery();
			  if(rs.next()){
			  	result = "1;"+rs.getString(4);
			  }else{
			  	result = "0;";
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println(result);
		  
	}
}
