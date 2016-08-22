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
	String action = request.getParameter("action");
	
	String strCategoryId = request.getParameter("categoryid");
	int categoryId = 0;
	if(null != strCategoryId && !strCategoryId.trim().equals("")) 
	{
		categoryId = Integer.parseInt(strCategoryId);
	}
	
	if( null != action && action.trim().equals("add"))
	{
		String name = request.getParameter("name");
		String descr = request.getParameter("descr");
		double normalPrice = Double.parseDouble(request.getParameter("normalprice"));
		double memberPrice = Double.parseDouble(request.getParameter("memberprice"));
		
		Category c = Category.loadById(categoryId);
		if(!c.isLeaf())
		{
			out.println("非叶子节点不能添加商品");
			return;
		}
		
		Product p = new Product();
		p.setId(-1);
		p.setName(name);
		p.setDescr(descr);
		p.setNormalPrice(normalPrice);
		p.setMemberPrice(memberPrice);
		p.setPdate(new Timestamp(System.currentTimeMillis()));
		p.setCategoryId(categoryId);
		
		ProductMgr.getInstance().addProduct(p);
		
		System.out.println("product add sucessfully");
	}
%>
<title>添加商品</title>

<script type = "text/javascript">
	var arrLeaf = new Array();
	function checkdata()
	{
		if(arrLeaf[document.form.categoryid.selectedIndex] != "leaf")
		{
			alert("不能选择非叶子节点");
			documemnt.form.categoryid.focus();
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<center>添加商品</center>
	<form name="form" action="productadd.jsp" method="post" onsubmit="return checkdata()" >
		<input type="hidden" name="action" value="add">
		<table>
			<tr>
				<td>商品名称:</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>商品描述:</td>
				<td><textarea name="descr" rows="8" cols="40"></textarea></td>
			</tr>
			<tr>
				<td>普通价:</td>
				<td><input type="text" name="normalprice"</td>
			</tr>
			<tr>
				<td>会员价:</td>
				<td><input type="text" name="memberprice"</td>
			</tr>
			<tr>
				<td>类别ID:</td>
				<td>
					<select name="categoryid">
						<option value="0">所有类别</option>
						<script type = "text/javascript">
							arrLeaf[0] = "noleaf";
						</script>
						<%
							List<Category> categories = Category.getCategories();
							int index = 1;
							for(Iterator<Category> it=categories.iterator();it.hasNext();)
							{
								Category c = it.next();
								String preStr = "";
								for(int i=1;i<c.getGrade();i++)
								{
									preStr += "--";
								}
								%>
								<script type = "text/javascript">
									arrLeaf[<%= index %>] = '<%= c.isLeaf() ? "leaf":"noleaf" %>'; 
								</script>
								<option value="<%=c.getId()%>" <%= c.getId() == categoryId ? "selected":"" %>><%=preStr+c.getName()%></option>
						<%	
							index++;
						} %>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交"></td>
			</tr>
		</table>
	</form>
</body>
</html>