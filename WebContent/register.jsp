<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import = "com.lyj.shopping.*" %>
<%@ page import = "java.sql.*" %>

<%
	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	if( null != action && action.trim().equals("register"))
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String addr = request.getParameter("addr");
		User u = new User();
		u.setUsername(username);
		u.setPassword(password);
		u.setPhone(phone);
		u.setAddr(addr);
		u.setRdate(new Timestamp(System.currentTimeMillis()));
		u.save();
		System.out.println("register sucessfully");
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>表单</title>
<script type="text/javascript" src="script/regcheckdata.js"></script>
</head>
<body>
<form name="form" action="register.jsp" method="post" onSubmit="return checkdata()">
	  <input type="hidden" name=action value=register>
      <table width=750 align="center" border="2">
	      <tr>
	      	<td colspan="2" align="center">用户注册</td>
	      </tr>
	      
	      <tr>
	      	<td>用户名：</td>
	      	<td>
	      		<input type="text" name="username" id="userid" size="30" maxlength="10" onblur="validate()">
	      		<div id="usermsg"></div>
	      	</td>
	      </tr>
	      
	      <tr>
	      	<td>密码：</td>
	      	<td><input type="password" name="password" size="15" maxlength="12"></td>
	      </tr>
	      
	      <tr>
	      	<td>密码确认:</td>
	      	<td><input type="password" name="password2" size=15 maxlength="12"></td>
	      </tr> 
	      
	      <tr>
	      	<td>电话</td>
	      	<td><input type="text" name="phone"</td>
	      </tr>
	      
	      <tr>
	      	<td>送货地址</td>
	      	<td><textarea rows="12" cols="80" name="addr"></textarea></td>
	      </tr> 
	      
	      <tr>
	      	<td></td>
	      	<td><input type="submit" value="提交"> <input type="reset" value="重置"></td>
	      </tr>
      </table>
</form>
</body>
</html>