<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String action = request.getParameter("action");
	if(null != action && action.equals("login"))
	{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if(null == username || !username.equals("admin"))
		{
			out.println("用户名不正确");
		}
		else if(null == password || !password.equals("admin"))
		{
			out.println("密码不正确");
		}
		else
		{
			session.setAttribute("admin","true");
			response.sendRedirect("adminindex.jsp");
		}
	}
%>
<title>Admin 登录</title>
</head>
<body>

<form name="form" action="login.jsp" method="post">
		<input type="hidden" name="action" value="login">
		<table width="750" align="center" border="2">
			<tr>
				<td colspan="2" align="center">管理员登录</td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td>
					<input type="text" name="username" size="30" maxlength="10">
				</td>
			</tr>
			<tr>
				<td>密码:</td>
				<td>
					<input type="text" name="password" size="15" maxlength="12">
				</td>
			<tr>
				<td>
					<input type="submit" value="登录">
					<input type="reset" value="重置">
				</td>
			</tr> 
		</table>
</form>

</body>
</html>