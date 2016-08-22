<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	int id = Integer.parseInt(request.getParameter("id"));
	Product p = ProductMgr.getInstance().loadById(id);
	
%>
<title>商品详情</title>

</head>
<body>
	<center>商品详情</center>
	<img src="http://d1.tuanimg.com/imagev2/site/464x260.c44d6696bf7844ae160a63d7142f022a.232x130.jpg" 
		 width="200" height="50" />
	<br>
	<%= p.getName() %><br><br>
	<%= p.getDescr() %><br><br>
	<%= p.getNormalPrice() %><br><br>
	<%= p.getMemberPrice() %><br><br>
	<a href="buy.jsp?id=<%=id %>">我买了</a>
</body>
</html>