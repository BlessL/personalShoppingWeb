<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>产品图片上传</title>

<%
	String strId = request.getParameter("id");
	if(null == strId || strId.trim().equals(""))
	{
		out.println("选择的商品有误");
		return;
	}
	
	int id = Integer.parseInt(strId);
%>
</head>
<body>
	<form action = "../FileUpload" method = "post" enctype = "multipart/form-data" name = "form1">
		<input type="hidden" name="id" value="<%= id %>">
		<input type="file"  name="file">
		<input type="submit" name="submit" value="upload">
	</form>
</body>
</html>