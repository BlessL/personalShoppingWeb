package com.lyj.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lyj.shopping.Category;
import com.lyj.shopping.Product;
import com.lyj.shopping.util.DB;

/**
 * @author yujin.liu
 */
public class ProductMySQLDAO implements ProductDAO
{

	@Override
	public List<Product> getProducts()
	{
		Connection conn = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();
		try
		{
			conn = DB.getConn();
			String sql = "select * from product";
			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryId"));

				list.add(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return list;
	}

	@Override
	public List<Product> getProducts(int pageNo, int pageSize)
	{
		Connection conn = null;
		ResultSet rs = null;

		List<Product> list = new ArrayList<Product>();
		try
		{
			conn = DB.getConn();
			String sql = "select * from product limit " + (pageNo - 1) * pageSize + "," + pageSize;
			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryId"));

				list.add(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return list;
	}

	/**
	 * @category 分页获取商品列表，返回总页数
	 */
	@Override
	public int getProducts(List<Product> products, int pageNo, int pageSize)
	{
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		int pageCount = 0;
		try
		{
			conn = DB.getConn();
			rsCount = DB.executeQuery(conn, "select count(1) from product");
			rsCount.next();
			pageCount = (rsCount.getInt(1) + pageSize - 1) / pageSize;
			String sql = "select p.id, p.name, p.descr, p.normalprice, p.memberprice, p.pdate, p.categoryid, c.id cid, c.name cname, c.descr cdescr, c.pid, c.isleaf, c.grade "
					+ "from product p join category c on (p.categoryid = c.id) limit " + (pageNo - 1) * pageSize + ","
					+ pageSize;
			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryid"));

				Category c = new Category();
				c.setId(rs.getInt("cid"));
				c.setName(rs.getString("cname"));
				c.setDescr(rs.getString("cdescr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getInt("isleaf") == 0 ? true : false);
				c.setGrade(rs.getInt("grade"));

				p.setCategory(c);

				products.add(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return pageCount;
	}

	@Override
	public int findProducts(List<Product> products, int[] categoryId, String keyword, double lowNormalPrice,
			double highNormalPrice, double lowMemberPrice, double highMemberPrice, Date startDate, Date endDate,
			int pageNo, int pageSize)
	{

		Connection conn = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		int pageCount = 1;
		try
		{
			conn = DB.getConn();
			String sql = "select * from product where 1=1";
			String sqlCount;
			String strId = "";

			if (null != categoryId && categoryId.length > 0)
			{
				strId += "(";
				for (int i = 0; i < categoryId.length; i++)
				{
					if (i < categoryId.length - 1)
					{
						strId += categoryId[i] + ",";
					}
					else
					{
						strId += categoryId[i];
					}
				}
				strId += ")";
				sql += " and categoryid in " + strId;
			}

			if (null != keyword && !keyword.trim().equals(""))
			{
				sql += " and name like '%" + keyword + "%' or descr like '%" + keyword + "%'";
			}

			if (lowNormalPrice >= 0)
			{
				sql += " and normalprice > " + lowNormalPrice;
			}

			if (highNormalPrice > 0)
			{
				sql += " and normalprice < " + highNormalPrice;
			}

			if (lowMemberPrice >= 0)
			{
				sql += " and memberprice > " + lowMemberPrice;
			}

			if (highMemberPrice > 0)
			{
				sql += " and memberprice < " + highMemberPrice;
			}

			if (null != startDate)
			{
				sql += " and pdate > '" + new SimpleDateFormat("yyyy-MM-dd").format(startDate) + "'";
			}

			if (null != endDate)
			{
				sql += " and pdate < '" + new SimpleDateFormat("yyyy-MM-dd").format(endDate) + "'";
			}

			sqlCount = sql.replaceFirst("select \\*", "select count(*)");
			rsCount = DB.executeQuery(conn, sqlCount);
			rsCount.next();
			pageCount = (rsCount.getInt(1) + pageSize - 1) / pageSize;

			sql += " limit " + (pageNo - 1) * pageSize + "," + pageSize;

			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryId"));

				products.add(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}

		return pageCount;
	}

	@Override
	public List<Product> findProducts(String name)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProductByCategoryId(int categoryId)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteProductByCategoryId(int[] idArray)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @category 更新商品
	 */
	@Override
	public boolean updateProduct(Product p)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = DB.getConn();
			String sql = "update product set name=?, descr=?, normalprice=?, memberprice=?, categoryid=? where id=?";
			pstmt = DB.getPStmt(conn, sql);
			pstmt.setString(1, p.getName());
			pstmt.setString(2, p.getDescr());
			pstmt.setDouble(3, p.getNormalPrice());
			pstmt.setDouble(4, p.getMemberPrice());
			pstmt.setInt(5, p.getCategoryId());
			pstmt.setInt(6, p.getId());

			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeConn(conn);
		}
		return true;
	}

	/**
	 * @category 添加商品
	 */
	@Override
	public boolean addProductProduct(Product p)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = DB.getConn();
			String sql = "insert into product values(null,?,?,?,?,?,?)";
			pstmt = DB.getPStmt(conn, sql);
			pstmt.setString(1, p.getName());
			pstmt.setString(2, p.getDescr());
			pstmt.setDouble(3, p.getNormalPrice());
			pstmt.setDouble(4, p.getMemberPrice());
			pstmt.setTimestamp(5, p.getPdate());
			pstmt.setInt(6, p.getCategoryId());

			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeConn(conn);
		}
		return true;
	}

	/**
	 * @category 根据id 查询商品信息
	 */
	@Override
	public Product loadById(int id)
	{
		Product p = null;
		Connection conn = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConn();
			String sql = "select p.id, p.name, p.descr, p.normalprice, p.memberprice, p.pdate, p.categoryid, c.id cid, c.name cname, c.descr cdescr, c.pid, c.isleaf, c.grade "
					+ "from product p join category c on (p.categoryid = c.id) where p.id = " + id;
			rs = DB.executeQuery(conn, sql);

			if (rs.next())
			{
				p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryid"));

				Category c = new Category();
				c.setId(rs.getInt("cid"));
				c.setName(rs.getString("cname"));
				c.setDescr(rs.getString("cdescr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getInt("isleaf") == 0 ? true : false);
				c.setGrade(rs.getInt("grade"));

				p.setCategory(c);

			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return p;
	}

	/**
	 * @category 获取最新信息
	 */
	@Override
	public List<Product> getLateastProducts(int count)
	{
		Connection conn = null;
		ResultSet rs = null;
		List<Product> list = new ArrayList<Product>();
		try
		{
			conn = DB.getConn();
			String sql = "select * from product order by pdate desc limit 0," + count;
			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				Product p = new Product();
				p.setId(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setDescr(rs.getString("descr"));
				p.setNormalPrice(rs.getDouble("normalprice"));
				p.setMemberPrice(rs.getDouble("memberprice"));
				p.setPdate(rs.getTimestamp("pdate"));
				p.setCategoryId(rs.getInt("categoryId"));

				list.add(p);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return list;
	}
}
