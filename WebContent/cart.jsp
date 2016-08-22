<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>

<%
	Cart cart = (Cart)session.getAttribute("cart");
	if(null == cart)
	{
		cart = new Cart();
		session.setAttribute("cart", cart);
	}
%>

<%
	if(null == cart)
	{
		out.print("没有任何购物项");
		return;
	}
%>
<center>你买了以下商品：</center>
<form action="cartupdate.jsp" method="post">
<table border=1 align="center">
	<tr>
		<td>商品ID</td>
		<td>商品名称</td>
		<td>商品价格</td>
		<td>商品数量</td>
	</tr>
	
	<%
		List<CartItem> items = cart.getItems();
		for(int i=0; i<items.size();i++)
		{
			CartItem ci = items.get(i);
		%>
		<tr>
		<td><%= ci.getProductId() %></td>
		<td><%= ProductMgr.getInstance().loadById(ci.getProductId()).getName() %></td>
		<td><%= ci.getPrice() %></td>
		<td>
			<input type="text" size=4 name="<%= "p" + ci.getProductId()%>" value="<%= ci.getCount() %>">
		</td>
	</tr>
		
	   <%}%>
</table>
<br>
<center>
	共<%= Math.round(cart.getTotalPrice()*100) / 100.0 %> 元 <br>
	<input type="submit" value="修改数量">
	<input type="button" value="确认订单" onclick="document.location.href='confirm.jsp'">
</center>
</form>
