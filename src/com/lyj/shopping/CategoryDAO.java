package com.lyj.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lyj.shopping.util.DB;

/**
 * @author yujin.liu
 */
public class CategoryDAO
{
	/**
	 * @category 往数据库保存category
	 */
	public static void save(Category c)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DB.getConn();
		String sql = "";
		if (-1 == c.getId())
		{
			sql = "insert into category values(null,?,?,?,?,?)";
		}
		else
		{
			sql = "insert into category values(" + c.getId() + ",?,?,?,?,?)";
		}

		try
		{
			pstmt = DB.getPStmt(conn, sql);
			pstmt.setString(1, c.getName());
			pstmt.setString(2, c.getDescr());
			pstmt.setInt(3, c.getPid());
			pstmt.setInt(4, c.isLeaf() ? 0 : 1);
			pstmt.setInt(5, c.getGrade());
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeConn(conn);
		}
	}

	/**
	 * @category connection 只连一次
	 */
	public static List<Category> getCategories(List<Category> list, int id)
	{
		Connection conn = null;
		try
		{
			conn = DB.getConn();
			getCategories(conn, list, id);
		}
		finally
		{
			DB.closeConn(conn);
		}
		return list;
	}

	/**
	 * @category 获取类别
	 */
	private static void getCategories(Connection conn, List<Category> list, int id)
	{
		ResultSet rs = null;
		try
		{
			String sql = "select * from category where pid =" + id;
			rs = DB.executeQuery(conn, sql);
			while (rs.next())
			{
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescr(rs.getString("descr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getInt("isleaf") == 0 ? true : false);
				c.setGrade(rs.getInt("grade"));
				list.add(c);
				if (!c.isLeaf())
				{
					// 递归查询子节点
					getCategories(list, c.getId());
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
		}
	}

	/**
	 * @category 保存子节点
	 */
	public static void addChildCategory(int pid, String name, String descr)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConn();
			conn.setAutoCommit(false);
			rs = DB.executeQuery(conn, "select * from category where id=" + pid);

			int parentGrade = 0;
			if (rs.next())
			{
				parentGrade = rs.getInt("grade");
			}

			// 存储新的 category
			String sql = "insert into category value(null,?,?,?,?,?)";

			pstmt = DB.getPStmt(conn, sql);
			pstmt.setString(1, name);
			pstmt.setString(2, descr);
			pstmt.setInt(3, pid);
			pstmt.setInt(4, 0);
			pstmt.setInt(5, parentGrade + 1);
			pstmt.executeUpdate();

			// 更新父节点，设置为非叶节点
			DB.executeUpdate(conn, "update category set isLeaf = 1 where id = " + pid);

			conn.commit();
			conn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			try
			{
				conn.rollback();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
	}

	/**
	 * @param id
	 * @param pid
	 */
	public static void delete(int id, int pid)
	{
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @return
	 */
	public static Category loadById(int id)
	{
		Connection conn = null;
		ResultSet rs = null;
		Category c = new Category();
		try
		{
			conn = DB.getConn();
			String sql = "select * from category where id =" + id;
			rs = DB.executeQuery(conn, sql);
			if (rs.next())
			{
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescr(rs.getString("descr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getInt("isleaf") == 0 ? true : false);
				c.setGrade(rs.getInt("grade"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return c;
	}

	/**
	 * @category 获取子类别
	 */
	public static List<Category> getChilds(Category parent)
	{

		Connection conn = null;
		ResultSet rs = null;
		List<Category> list = new ArrayList<Category>();
		try
		{
			conn = DB.getConn();
			String sql = "select * from category where pid =" + parent.getId();
			rs = DB.executeQuery(conn, sql);
			while (rs.next())
			{
				Category c = new Category();
				c.setId(rs.getInt("id"));
				c.setName(rs.getString("name"));
				c.setDescr(rs.getString("descr"));
				c.setPid(rs.getInt("pid"));
				c.setLeaf(rs.getInt("isleaf") == 0 ? true : false);
				c.setGrade(rs.getInt("grade"));
				list.add(c);
			}
		}
		catch (SQLException e)
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
