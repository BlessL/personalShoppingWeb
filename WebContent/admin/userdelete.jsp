<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="com.lyj.shopping.*" %>
<%@ page include file="sessioncheck.jsp" %>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	UserManager.deleteUser(id);
%>
<title>用户删除</title>
</head>
<body>
	用户删除成功！
</body>
</html>