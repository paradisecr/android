<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*,com.xuanke.DBHelper.*" pageEncoding="utf-8"%>
<%
  String result,userid,pass;
  userid = request.getParameter("userid");
  pass = request.getParameter("pass");
  result = "1;孙胖子";
  response.setCharacterEncoding("UTF-8");
  DBHelper db = new DBHelper();
  Connection conn = db.getConnection();
  Statement stmt = conn.createStatement();
  PreparedStatement pstmt = conn.prepareStatement("select * from student where Sno=? and Spass=?");
  pstmt.setString(1, userid);
  pstmt.setString(2,pass);
  ResultSet rs = pstmt.executeQuery();
  if(rs.next()){
  	result = "1;"+rs.getString(4);
  }else{
  	result = "0;0";
  }
  out.print(result);
  System.out.println(userid+"-------"+pass+"登陆返回值："+result);
  rs.close();
  pstmt.close();
  conn.close();
%>

