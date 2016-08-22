<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>
<%@ include file="sessioncheck.jsp" %>
<%!  private static final int PAGE_SIZE = 3; %>

<%  
	String strPageNo = request.getParameter("pageno"); 
	int pageNo = 1;
	if(null != strPageNo)
	{
		pageNo = Integer.parseInt(strPageNo);	
	}
	
	if(pageNo < 1) pageNo = 1;
%>

<%	List<Product> products = new ArrayList<Product>();
	int pageCount = ProductMgr.getInstance().getProducts(products, pageNo, PAGE_SIZE); 
	if(pageNo > pageCount)
	{
		pageNo = pageCount;
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品列表</title>
</head>
<body>

		<table border="1" align="center">
			<tr>
				<td>ID</td>
				<td>商品名称</td>
				<td>商品描述</td>
				<td>普通价</td>
				<td>会员价</td>
				<td>上架日期</td>
				<td>类别id</td>
				<td>操作</td>
			</tr>
			
			<%
				for(Iterator<Product> it = products.iterator();it.hasNext();)
				{
					Product p = it.next();
			%>
				<tr>
					<td><%= p.getId() %></td>
					<td><%= p.getName() %></td>
					<td><%= p.getDescr() %></td>
					<td><%= p.getNormalPrice() %></td>
					<td><%= p.getMemberPrice() %></td>
					<td><%= p.getPdate() %></td>
					<td><%= p.getCategory().getName() %></td>
					<td>
						<a href="productdelete.jsp?id=<%= p.getId() %>" target="detail">删除</a>
						&nbsp;&nbsp;
						<a href="productmodify.jsp?id=<%= p.getId()%>" target="detail">修改</a>
						&nbsp;&nbsp;
						<a href="productimageup.jsp?id=<%= p.getId()%>" target="detail">上传图片</a>
					</td>
				</tr>
			<% }%>
		</table>
		<center>
			第<%= pageNo %>页
			&nbsp;
			共<%= pageCount %>页
			&nbsp;
			<a href="productlist.jsp?pageno=<%= pageNo-1 %>">上一页</a>
			&nbsp;
			<a href="productlist.jsp?pageno=<%= pageNo+1 %>">下一页</a>
			&nbsp;
			<a href="productlist.jsp?pageno=<%= pageCount %>">最后一页</a>
		</center>
</body>
</html>