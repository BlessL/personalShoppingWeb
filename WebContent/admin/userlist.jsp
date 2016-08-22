<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>
<%@ include file="sessioncheck.jsp" %>
<%
	List<User> users = UserManager.getUsers();
%>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<td>用户ID</td>
			<td>用户名</td>
			<td>电话</td>
			<td>地址</td>
			<td>注册时间</td>
			<td>操作</td>
		</tr>
		
		<%
			for(Iterator<User> it = users.iterator();it.hasNext();)
			{
				User u = it.next();
		%>
			<tr>
				<td><%=u.getId() %></td>
				<td><%=u.getUsername() %></td>
				<td><%=u.getPhone() %></td>
				<td><%=u.getAddr() %></td>
				<td><%=u.getRdate() %></td>
				<td>
					<a href="userdelete.jsp?id=<%=u.getId()%>">删除</a>
				</td>
			</tr>
		<% }%>
	</table>
</body>
</html>