<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" import="java.util.*,com.xuanke.DBHelper.*" pageEncoding="utf-8"%>
<%
  String result1,result2,userid,pass;
  userid = request.getParameter("userid");
  result1 = "";
  result2 = "";
  response.setCharacterEncoding("UTF-8");
  DBHelper db = new DBHelper();
  Connection conn = db.getConnection();
  Statement stmt = conn.createStatement();
  PreparedStatement pstmt = conn.prepareStatement("select * from course where Cno in(select Cno from choose where Sno=?)");
  pstmt.setString(1, userid);
  ResultSet rs = pstmt.executeQuery();
  while(rs.next()){
  	if(result1.equals("")){
  	result1 += rs.getString(2);
  	result2 += rs.getString(3);
  	}else{
  	result1 += ";";
  	result1 += rs.getString(2);
  	result2 += ";";
  	result2 += rs.getString(3);
  	}
  }
  result1 += "@";
  result1 += result2;
  out.print(result1);
   System.out.println(userid);
  System.out.println("查看课程返回值："+result1);
  rs.close();
  pstmt.close();
  conn.close();
%>