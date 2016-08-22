package com.lyj.shopping.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author yujin.liu
 *
 */
public class DB
{
	/**
	 * @category 单例模式
	 */
	private static DB db;

	static
	{
		db = new DB();
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private DB()
	{
	}

	/**
	 * @category 获取数据库连接
	 */
	public static Connection getConn()
	{
		Connection conn = null;
		try
		{
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping?user=root&password=root");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * @category 关闭连接
	 */
	public static void closeConn(Connection conn)
	{
		if (null != conn)
		{
			try
			{
				conn.close();
				conn = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * @category 获取statement
	 */
	public static Statement getStmt(Connection conn)
	{
		Statement stmt = null;
		try
		{
			stmt = conn.createStatement();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return stmt;
	}

	/**
	 * @category 关闭statement
	 */
	public static void closeStmt(Statement stmt)
	{
		if (null != stmt)
		{
			try
			{
				stmt.close();
				stmt = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * @category 获取prepareStatement
	 */
	public static PreparedStatement getPStmt(Connection conn, String sql)
	{
		PreparedStatement pStmt = null;
		try
		{
			pStmt = conn.prepareStatement(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pStmt;
	}

	public static PreparedStatement getPStmt(Connection conn, String sql, boolean generateKey)
	{
		PreparedStatement pStmt = null;
		try
		{
			pStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return pStmt;
	}

	/**
	 * @category 查询
	 */
	public static ResultSet executeQuery(Statement stmt, String sql)
	{
		ResultSet rs = null;
		try
		{
			rs = stmt.executeQuery(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * @category 查询
	 */
	public static ResultSet executeQuery(Connection conn, String sql)
	{
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * @category 更新
	 */
	public static void executeUpdate(Connection conn, String sql)
	{
		PreparedStatement pstmt = null;

		try
		{
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @category 关闭 ResutlSet
	 */
	public static void closeResultSet(ResultSet rs)
	{
		if (null != rs)
		{
			try
			{
				rs.close();
				rs = null;
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

}
