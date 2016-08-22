package com.lyj.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lyj.shopping.Cart;
import com.lyj.shopping.CartItem;
import com.lyj.shopping.Product;
import com.lyj.shopping.SalesItem;
import com.lyj.shopping.SalesOrder;
import com.lyj.shopping.User;
import com.lyj.shopping.util.DB;

public class OrderMySQLDAO
{

	public void saveOrder(SalesOrder so)
	{
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rsKey = null;
		try
		{
			conn = DB.getConn();
			conn.setAutoCommit(false);

			String sql = "insert into salesorder values(null,?,?,?,?)";
			pstmt = DB.getPStmt(conn, sql, true);
			pstmt.setInt(1, so.getUser().getId());
			pstmt.setString(2, so.getAddr());
			pstmt.setTimestamp(3, so.getoDate());
			pstmt.setDouble(4, so.getStatus());
			pstmt.executeUpdate();

			rsKey = pstmt.getGeneratedKeys();
			rsKey.next();
			int key = rsKey.getInt(1);

			String sqlItem = "insert into salesitem values (null,?,?,?,?)";
			pstmt = DB.getPStmt(conn, sqlItem);
			Cart c = so.getCart();
			List<CartItem> items = c.getItems();
			for (int i = 0; i < items.size(); i++)
			{
				CartItem ci = items.get(i);
				pstmt.setInt(1, ci.getProductId());
				pstmt.setDouble(2, ci.getPrice());
				pstmt.setInt(3, ci.getCount());
				pstmt.setInt(4, key);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			try
			{
				conn.setAutoCommit(true);
				conn.rollback();
			}
			catch (SQLException e1)
			{
				e1.printStackTrace();
			}

		}
		finally
		{
			DB.closeStmt(pstmt);
			DB.closeResultSet(rsKey);
			DB.closeConn(conn);
		}
	}

	/**
	 * @category 获取订单列表
	 */
	public int getOrders(List<SalesOrder> orders, int pageNo, int pageSize)
	{
		Connection conn = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		int pageCount = 0;
		try
		{
			conn = DB.getConn();
			rsCount = DB.executeQuery(conn, "select count(1) from salesorder");
			rsCount.next();
			pageCount = (rsCount.getInt(1) + pageSize - 1) / pageSize;
			String sql = "select o.id, o.userid, o.addr, o.odate, o.status, u.id uid, u.username, u.phone, u.addr uaddr, u.rdate "
					+ "from salesorder o join user u on (o.userid = u.id) limit " + (pageNo - 1) * pageSize + ","
					+ pageSize;
			rs = DB.executeQuery(conn, sql);

			while (rs.next())
			{
				User u = new User();
				u.setId(rs.getInt("uid"));
				u.setUsername(rs.getString("username"));
				u.setPhone(rs.getString("phone"));
				u.setAddr(rs.getString("uaddr"));
				u.setRdate(rs.getTimestamp("rdate"));

				SalesOrder so = new SalesOrder();
				so.setId(rs.getInt("id"));
				so.setAddr(rs.getString("addr"));
				so.setoDate(rs.getTimestamp("odate"));
				so.setStatus(rs.getInt("status"));
				so.setUser(u);

				orders.add(so);

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

	/**
	 * @category 获取订单
	 */
	public SalesOrder loadById(int id)
	{
		Connection conn = null;
		ResultSet rs = null;
		SalesOrder so = null;
		try
		{
			conn = DB.getConn();
			String sql = "select o.id, o.userid, o.addr, o.odate, o.status, u.id uid, u.username, u.phone, u.addr uaddr, u.rdate "
					+ "from salesorder o join user u on (o.userid = u.id) where o.id = " + id;
			rs = DB.executeQuery(conn, sql);

			if (rs.next())
			{
				User u = new User();
				u.setId(rs.getInt("uid"));
				u.setUsername(rs.getString("username"));
				u.setPhone(rs.getString("phone"));
				u.setAddr(rs.getString("uaddr"));
				u.setRdate(rs.getTimestamp("rdate"));

				so = new SalesOrder();
				so.setId(rs.getInt("id"));
				so.setAddr(rs.getString("addr"));
				so.setoDate(rs.getTimestamp("odate"));
				so.setStatus(rs.getInt("status"));
				so.setUser(u);
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
		return so;
	}

	/**
	 * @category 获取订单列表项
	 */
	public List<SalesItem> getSalesItems(SalesOrder order)
	{
		Connection conn = null;
		ResultSet rs = null;
		List<SalesItem> items = new ArrayList<SalesItem>();
		try
		{
			conn = DB.getConn();
			String sql = "select o.id, o.userid, o.addr, o.odate, o.status, i.id itemid, i.productid, i.unitprice, i.pcount, i.orderid,"
					+ "p.id pid, p.name, p.descr, p.normalprice, p.memberprice, p.pdate, p.categoryid "
					+ "from salesorder o join salesitem i on (o.id = i.orderid) join product p on (i.productid = p.id) where o.id = "
					+ order.getId();
			rs = DB.executeQuery(conn, sql);
			while (rs.next())
			{
				Product product = new Product();
				product.setId(rs.getInt("pid"));
				product.setCategoryId(rs.getInt("categoryid"));
				product.setDescr(rs.getString("descr"));
				product.setName(rs.getString("name"));
				product.setNormalPrice(rs.getDouble("normalprice"));
				product.setMemberPrice(rs.getDouble("memberprice"));
				product.setPdate(rs.getTimestamp("pdate"));

				SalesItem si = new SalesItem();
				si.setOrder(order);
				si.setId(rs.getInt("itemid"));
				si.setUnitPrice(rs.getDouble("unitprice"));
				si.setCount(rs.getInt("pcount"));
				si.setProduct(product);

				items.add(si);
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
		return items;
	}

	/**
	 * @category 更改订单状态
	 */
	public void updateStatus(SalesOrder salesOrder)
	{
		Connection conn = null;
		try
		{
			conn = DB.getConn();
			String sql = "update salesorder set status = " + salesOrder.getStatus() + " where id = "
					+ salesOrder.getId();
			DB.executeUpdate(conn, sql);
		}
		finally
		{
			DB.closeConn(conn);
		}

	}
}
