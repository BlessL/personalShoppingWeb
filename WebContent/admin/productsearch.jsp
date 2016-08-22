<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ page import="java.sql.*,com.lyj.shopping.*,java.util.*" %>
<%@ include file="sessioncheck.jsp" %>

<%
	List<Category> categories = Category.getCategories();

	request.setCharacterEncoding("UTF-8");
	String action = request.getParameter("action");
	if(null != action && action.trim().equals("complexsearch"))
	{
		int pageNo = 1;
		String strPageNo = request.getParameter("pageno");
		if(null != strPageNo && !strPageNo.trim().equals(""))
		{
			pageNo = Integer.parseInt(strPageNo);
		}
		String keyword = request.getParameter("keyword");
		double lowNormalPrice = Double.parseDouble(request.getParameter("lownormalprice"));
		double highNormalPrice = Double.parseDouble(request.getParameter("highnormalprice"));
		double lowMemberPrice = Double.parseDouble(request.getParameter("lowmemberprice"));
		double highMemberPrice = Double.parseDouble(request.getParameter("highmemberprice"));
		int categoryId = Integer.parseInt(request.getParameter("categoryid"));
		
		int[] idArray; 
		if(0 == categoryId)
		{
			idArray = null;
		}
		else
		{
			idArray = new int[1];
			idArray[0] = categoryId;
		}
		
		Timestamp startDate ;
		String strStartDate = request.getParameter("startdate");
		if(null == strStartDate || strStartDate.trim().equals(""))
		{
			startDate = null;
		}
		else
		{
			startDate = Timestamp.valueOf(strStartDate);
		}

		Timestamp endDate ;
		String strEndDate = request.getParameter("enddate");
		if(null == strEndDate || strEndDate.trim().equals(""))
		{
			endDate = null;
		}
		else
		{
			endDate = Timestamp.valueOf(strEndDate);
		}
		List<Product> products = new ArrayList<Product>();
		int pageCount = ProductMgr.getInstance().findProducts(products, idArray, keyword, lowNormalPrice, highNormalPrice, lowMemberPrice, highMemberPrice, startDate, endDate, pageNo, 3);
%>
			<center>商品搜索</center>
			<table border="1" align="center">
			<tr>
				<td>ID</td>
				<td>商品名称</td>
				<td>商品描述</td>
				<td>普通价</td>
				<td>会员价</td>
				<td>上架日期</td>
				<td>类别id</td>
				<td>操作</td>
			</tr>
			
			<%
				for(Iterator<Product> it = products.iterator();it.hasNext();)
				{
					Product p = it.next();
			%>
				<tr>
					<td><%= p.getId() %></td>
					<td><%= p.getName() %></td>
					<td><%= p.getDescr() %></td>
					<td><%= p.getNormalPrice() %></td>
					<td><%= p.getMemberPrice() %></td>
					<td><%= p.getPdate() %></td>
					<td><%= p.getCategoryId() %></td>
					<td>
						<a href="productdelete.jsp?id=<%= p.getId() %>" target="detail">删除</a>
						&nbsp;&nbsp;
						<a href="productmodify.jsp?id=<%= p.getId()%>" target="detail">修改</a>
					</td>
				</tr>
			<% }%>
		</table>
		<center>
			共<%= pageCount %>页
			&nbsp;
			<a href="productsearch.jsp?action=<%= action %>&keyword=<%= keyword %>&lownormalprice=<%= lowNormalPrice %>&highnormalprice=<%= highNormalPrice %>&lowmemberprice=<%= lowMemberPrice %>&highmemberprice=<%= highMemberPrice %>&startdate=<%= strStartDate %>&enddate=<%= strEndDate %>&categoryid=<%= categoryId %>&pageno=<%= pageNo-1 %>">上一页</a>
			<a href="productsearch.jsp?action=<%= action %>&keyword=<%= keyword %>&lownormalprice=<%= lowNormalPrice %>&highnormalprice=<%= highNormalPrice %>&lowmemberprice=<%= lowMemberPrice %>&highmemberprice=<%= highMemberPrice %>&startdate=<%= strStartDate %>&enddate=<%= strEndDate %>&categoryid=<%= categoryId %>&pageno=<%= pageNo+1 %>">下一页</a>
		</center>
	
	<%
	return ;
	}
	%>

<title>添加类别</title>

<script type="text/javascript">
		function checkdata()
		{
			with(document.forms["complex"])
			{
				if(null == lownormalprice.value || "" == lownormalprice.value)
				{
					lownormalprice.value = -1;
				}
				if(null == highnormalprice.value || "" == highnormalprice.value)
				{
					highnormalprice.value = -1;
				}
				if(null == lowmemberprice.value || "" == lowmemberprice.value)
				{
					lowmemberprice.value = -1;
				}
				if(null == highmemberprice.value || "" == highmemberprice.value)
				{
					highmemberprice.value = -1;
				}
			}
		}
</script>
</head>
<body>
	<center>简单搜索</center>
	<from action="productsearch.jsp" method="post">
		<input type="hidden" name="simplesearch"> 
		类别：<select></select> 
		关键字:<input type="text" name="keyword">
		<input type="submit" value="搜一搜">
	</from>
	
	<center>复杂搜索</center>
	<form action="productsearch.jsp" method="post" onsubmit="checkdata()" name="complex">
		<input type="hidden" name="action" value="complexsearch">
		<table>
			<tr>
				<td>类别:</td>
				<td>
					<select name="categoryid">
						<option value="0">所有类别</option>
						<%
							for(Iterator<Category> it=categories.iterator();it.hasNext();)
							{
								Category c = it.next();
								String preStr = "";
								for(int i=1;i<c.getGrade();i++)
								{
									preStr += "--";
								}
								%>
								<option value="<%=c.getId()%>"><%=preStr+c.getName()%></option>
						<%	} %>
					</select>
				</td>
			</tr>
			<tr>
				<td>关键字</td>
				<td><input type="text" name="keyword"></td>
			</tr>
			<tr>
				<td>普通价</td>
				<td>
					From:<input type="text" name="lownormalprice">
					To  :<input type="text" name="highnormalprice">
				</td>
				
			</tr>
			<tr>
				<td>会员价</td>
				<td>
					From:<input type="text" name="lowmemberprice">
				    To  :<input type="text" name="highmemberprice">
				</td>
			</tr>
			<tr>
				<td>上架日期</td>
				<td>
					From:<input type="text" name="startdate">
				    To  :<input type="text" name="enddate">
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="搜一搜"></td>
			</tr>
		</table>
	</form>
</body>
</html>