package com.lyj.shopping;

import java.util.List;

import com.lyj.shopping.dao.OrderMySQLDAO;

public class OrderMgr
{
	private static OrderMgr om = null;

	static
	{
		if (null == om)
		{
			om = new OrderMgr();
			om.setDao(new OrderMySQLDAO());
		}
	}

	private OrderMgr()
	{
	}

	/**
	 * @category 单例模式
	 */
	public static OrderMgr getInstance()
	{
		return om;
	}

	OrderMySQLDAO dao = null;

	public OrderMySQLDAO getDao()
	{
		return dao;
	}

	public void setDao(OrderMySQLDAO dao)
	{
		this.dao = dao;
	}

	public void saveOrder(SalesOrder so)
	{
		dao.saveOrder(so);
	}

	public int getOrders(List<SalesOrder> orders, int pageNo, int pageSize)
	{
		return dao.getOrders(orders, pageNo, pageSize);
	}

	public SalesOrder loadById(int id)
	{
		return dao.loadById(id);
	}

	/**
	 * @category 获取订单项
	 */
	public List<SalesItem> getSalesItems(SalesOrder salesOrder)
	{
		return dao.getSalesItems(salesOrder);
	}

	/**
	 * @category 更新订单状态
	 */
	public void updateStatus(SalesOrder salesOrder)
	{
		dao.updateStatus(salesOrder);
	}

}
