package com.lyj.shopping;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.lyj.shopping.util.DB;

/**
 * @author yujin.liu
 *
 */
public class User
{
	private int id;
	private String username;
	private String password;
	private String addr;
	private String phone;
	private Timestamp rdate;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getAddr()
	{
		return addr;
	}

	public void setAddr(String addr)
	{
		this.addr = addr;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public Timestamp getRdate()
	{
		return rdate;
	}

	public void setRdate(Timestamp rdate)
	{
		this.rdate = rdate;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", addr=" + addr + ", phone="
				+ phone + ", rdate=" + rdate + "]";
	}

	/**
	 * @category 往数据库保存user
	 */
	public void save()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		conn = DB.getConn();
		String sql = "insert into user values(null,?,?,?,?,?)";
		pstmt = DB.getPStmt(conn, sql);
		try
		{
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, phone);
			pstmt.setString(4, addr);
			pstmt.setTimestamp(5, rdate);
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
	 * @category 更新user
	 */
	public void update()
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		try
		{
			conn = DB.getConn();
			String sql = "update user set username= ?,phone= ?,addr= ? where id= " + this.id;
			pstmt = DB.getPStmt(conn, sql);
			pstmt.setString(1, username);
			pstmt.setString(2, phone);
			pstmt.setString(3, addr);
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

}
