<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*,com.xuanke.DBHelper.*" pageEncoding="utf-8"%>
<%
  String result,userid;
  userid = request.getParameter("userid");
  result = request.getParameter("class");
  String classes[] = result.split(";");
  response.setCharacterEncoding("UTF-8");
  DBHelper db = new DBHelper();
  Connection conn = db.getConnection();
  Statement stmt = conn.createStatement();
  PreparedStatement pstmt = conn.prepareStatement("insert into choose(Sno,Cno) values(?,?)");
  System.out.print("插入参数："+ result);
  System.out.print("插入：");
  for(int i = 0; i<classes.length; i++){
  		pstmt.setString(1, userid);
  		pstmt.setString(2, classes[i]);
  		pstmt.executeUpdate();
  		System.out.print(classes[i]);
  }
  pstmt.close();
  conn.close();
%>