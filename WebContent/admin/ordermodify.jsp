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
	
	String action = request.getParameter("action");
	if(null != action && action.trim().equals("modify"))
	{
		int status = Integer.parseInt(request.getParameter("status"));
		so.setStatus(status);
		so.updateStatus();
	}
%>

<center>
	下单人：<%= so.getUser().getUsername() %>
	<form name="form" action=""  method="post">
		<input type="hidden" name="action" value="modify">
		<input type="hidden" name="id" value="<%=id %>">
		<select name="status">
			<option value="0">未处理</option>
			<option value="1">已处理</option>
			<option value="2">废单</option>
		</select>
		<br>
		<input type="submit" value="提交">
	</form>
</center>

<script type="text/javascript">
	for(var option in document.forms("form").status.options)
	{
		if(option.value = <%= so.getStatus() %>)
			document.forms("form").status.selectedIndex = option.index;
	}
</script>


