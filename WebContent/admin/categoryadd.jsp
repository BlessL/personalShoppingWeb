<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>
<%@ include file="sessioncheck.jsp" %>

<%
	request.setCharacterEncoding("UTF-8");
	String strPid = request.getParameter("pid");
	Integer pid = 0;
	if(null != strPid)
	{
		pid = Integer.parseInt(strPid);
	}
	String action = request.getParameter("action");
	if( null != action && action.trim().equals("add"))
	{
		String name = request.getParameter("name");
		String descr = request.getParameter("descr");
		if(0 == pid)
		{
			Category.addTopCategory(name, descr);
			System.out.println("top category add sucessfully");
		}
		else
		{
			Category.addChildCategory(pid, name, descr);
			System.out.println("child category add sucessfully");
		}
	}
%>
<title>添加类别</title>
</head>
<body>
	<center>添加类别</center>
	<form action="categoryadd.jsp" method="post">
		<input type="hidden" name="action" value="add">
		<input type="hidden" name="pid" value="<%= pid %>">
		<table>
			<tr>
				<td>类别名称</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>类别描述</td>
				<td><textarea type="text" name="descr" rows="8" cols="40"></textarea></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>