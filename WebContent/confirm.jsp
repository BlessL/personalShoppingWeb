<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	Cart cart = (Cart)session.getAttribute("cart");
	if(null == cart)
	{
		cart = new Cart();
		session.setAttribute("cart", cart);
	}
	
	User u = (User)session.getAttribute("user");
	if(null == u)
	{
		out.println("你还没有登录，如果继续，将按市场价购买");
		//out.println("<a href= confirmusernormal.jsp>继续</a><br>");
		out.println("<a href=login.jsp>登录</a>");
	}
%>

<table border=1 align="center">
	<tr>
		<td>商品ID</td>
		<td>商品名称</td>
		<td>商品价格<%= (null==u? "市场价":"会员价") %></td>
		<td>商品数量</td>
	</tr>
	
	<%
		List<CartItem> items = cart.getItems();
		for(int i=0; i<items.size();i++)
		{
			CartItem ci = items.get(i);
			Product p = ProductMgr.getInstance().loadById(ci.getProductId());
		%>
		<tr>
		<td><%= ci.getProductId() %></td>
		<td><%= p.getName() %></td>
		<td><%= (null == u)? p.getNormalPrice():p.getMemberPrice() %></td>
		<td>
			<%= ci.getCount() %>
		</td>
	</tr>
		
	   <%}%>
</table>

<center>
	共<%= Math.round(cart.getTotalPrice()*100) / 100.0 %> 元 <br>
	
	<%
		if(null != u)
		{
	%>		
			欢迎您<%= u.getUsername() %>请确认您的收货信息
			
	<%
		}
	%>
	
	<form action="order.jsp" method="post">
		送货信息：<br>
		<textarea name="addr">
			<%=( u==null ? "": u.getAddr()) %>
		</textarea><br>
		<input type="submit" value="下单">
	</form>
</center>
