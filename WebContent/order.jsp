<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	User u = (User)session.getAttribute("user");
	if(null == u)
	{
		u = new User();
		u.setId(-1);
	}
	
	Cart cart = (Cart)session.getAttribute("cart");
	if(null == cart)
	{
		out.println("没有任何购物项");
		return ;
	}
%>

<%
	String addr = request.getParameter("addr");
	SalesOrder so = new SalesOrder();
	so.setAddr(addr);
	so.setCart(cart);
	so.setUser(u);
	so.setoDate(new Timestamp(System.currentTimeMillis()));
	so.setStatus(0);
	
	so.save();
	
	session.removeAttribute("cart");
%>

<center>
	欢迎您在本站购物！祝您越买越多！
</center>
