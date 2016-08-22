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
	request.setCharacterEncoding("UTF-8");
	int id = Integer.parseInt(request.getParameter("id"));
	
	CartItem ci = new CartItem();
	Product p = ProductMgr.getInstance().loadById(id);
	ci.setProductId(id);
	ci.setPrice(p.getNormalPrice());
	ci.setCount(1);
	
	cart.add(ci);
	
	response.sendRedirect("cart.jsp");
%>
