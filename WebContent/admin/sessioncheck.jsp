<%
	String admin = (String)session.getAttribute("admin");
	if(null == admin || !admin.equals("true"))
	{
		response.sendRedirect("login.jsp");
	}
%>