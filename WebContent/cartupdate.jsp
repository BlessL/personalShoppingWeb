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
		out.println("没有任何购物项");
		return;
	}

	List<CartItem> items = cart.getItems();
	for(int i=0 ; i<items.size() ; i++)
	{
		CartItem ci = items.get(i);
		String strCount = request.getParameter("p"+ ci.getProductId());
		if(null != strCount && !strCount.trim().equals(""))
		{
			ci.setCount(Integer.parseInt(strCount));
		}
	}
%>

<center>修改成功</center>
3秒后返回购物车
<div id = "num">
</div>

<script language="javascript">
	var leftTime = 3000;
	function go()
	{
		document.getElementById("num").innerText = leftTime/1000;
		leftTime -= 1000;
		if(leftTime < 0)
		{
			document.location.href = "cart.jsp";
		}
		
	}
	setInterval(go,1000);
</script>
