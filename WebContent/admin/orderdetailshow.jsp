<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>

<%
	int id = Integer.parseInt(request.getParameter("id"));
	SalesOrder so = OrderMgr.getInstance().loadById(id);
%>

<script type ="text/javascript">
	function showProductInfo(descr)
	{
		document.getElementById("productInfo").innerHTML="<font size=3 color=red>" + descr + "</font>";
	}

</script>

<center>
	下单人：<%= so.getUser().getUsername() %>
	明   细：<br>
	
	<table border=1 align="center">
		<tr>
			<td>商品名称</td>
			<td>商品价格</td>
			<td>商品数量</td>
		</tr>
		
		<%
			List<SalesItem> items = so.getItems();
			for(int i=0; i<items.size();i++)
			{
				SalesItem si = items.get(i);
		%>
		<tr>
			<td onmouseover="showProductInfo('<%= si.getProduct().getDescr() %>')"><%= si.getProduct().getName() %></td>
			<td><%= si.getUnitPrice() %></td>
			<td><%= si.getCount() %></td>
		</tr>
		<%
		    }
		 %>
	</table>
	<div style="border:5px double purple; width:200;" id="productInfo">
		&nbsp;
	</div>
</center>


