<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>
<%@ include file="sessioncheck.jsp" %>

<%	List<Category> categories = Category.getCategories(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>类别列表</title>
</head>
<body>

		<table border="1" align="center">
			<tr>
				<td>ID</td>
				<td>类别名称</td>
				<td>父类id</td>
				<td>等级</td>
				<td>子类别添加</td>
			</tr>
			
			<%
				for(Iterator<Category> it = categories.iterator();it.hasNext();)
				{
					Category c = it.next();
					String preStr = "";
					for(int i=1;i<c.getGrade();i++)
					{
						preStr += "----";
					}
				
			%>
				<tr>
					<td><%= c.getId() %></td>
					<td><%= preStr + c.getName() %></td>
					<td><%= c.getPid() %></td>
					<td><%= c.getGrade() %></td>
					<td>
						<a href="categoryadd.jsp?pid=<%= c.getId() %>">添加</a>
						
					<% if(c.isLeaf()){ %>
						<a href="productadd.jsp?categoryid=<%= c.getId() %>">在该类别下面添加商品</a>
					<%} %>
					</td>
				</tr>
			<% }%>
		</table>
</body>
</html>