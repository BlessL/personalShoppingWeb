<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自助服务</title>
<%@ page import="com.lyj.shopping.*" %>
<%@ page import="java.sql.*" %>
<% 
	User u = (User) session.getAttribute("user");
	if(null == u)
	{
		out.println("You havn't logged in!");
		return;
	}
%>
</head>
<body>
	<a href="usermodify.jsp">修改个人信息</a>
</body>
</html>