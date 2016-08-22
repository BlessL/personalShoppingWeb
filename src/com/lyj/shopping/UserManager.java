/**
 * 
 */
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
 *
 */
public class UserManager
{
	/**
	 * @category 获取用户列表
	 */
	public static List<User> getUsers()
	{
		List<User> list = new ArrayList<User>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConn();
			String sql = "select * from User";
			pstmt = DB.getPStmt(conn, sql);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setAddr(rs.getString("addr"));
				u.setPassword(rs.getString("password"));
				u.setPhone(rs.getString("phone"));
				u.setRdate(rs.getTimestamp("rdate"));
				list.add(u);
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

	/**
	 * @category 删除用户
	 */
	public static void deleteUser(int id)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = DB.getConn();
			String sql = "delete from user where id = " + id;
			pstmt = DB.getPStmt(conn, sql);
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeConn(conn);
		}
	}

	/**
	 * @category 验证用户名和密码
	 */
	public static User validate(String username, String password)
			throws UserNotFoundException, PasswordNotCorrectException
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User u = null;
		try
		{
			conn = DB.getConn();
			String sql = "select * from user where username = '" + username + "'";
			pstmt = DB.getPStmt(conn, sql);
			rs = pstmt.executeQuery();
			if (!rs.next())
			{
				throw new UserNotFoundException();
			}
			else if (!rs.getString("password").equals(password))
			{
				throw new PasswordNotCorrectException();
			}
			else
			{
				u = new User();
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setPhone(rs.getString("phone"));
				u.setAddr(rs.getString("addr"));
				u.setRdate(rs.getTimestamp("rdate"));
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeResultSet(rs);
			DB.closeConn(conn);
		}
		return u;
	}
}
