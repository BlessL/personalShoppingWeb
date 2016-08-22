<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.lyj.shopping.*,java.util.*" %>
<%
	int id = Integer.parseInt(request.getParameter("id"));
	Category parent = Category.loadById(id);
	
	List<Category> childs = parent.getChilds();
	StringBuffer buf = new StringBuffer();
	for(int i=0; i<childs.size(); i++)
	{
		Category c = childs.get(i);
		buf.append(c.getId() + "," + c.getName() + "-");
	}
	buf.deleteCharAt(buf.length() -1);
	
	response.setHeader("Cache-Controller","no-store");
	response.setDateHeader("Expires",0);
	response.getWriter().write(buf.toString());
%>