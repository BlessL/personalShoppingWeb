package com.lyj.shopping;

import java.util.Date;
import java.util.List;

import com.lyj.shopping.dao.ProductDAO;
import com.lyj.shopping.dao.ProductMySQLDAO;

/**
 * @author yujin.liu
 *
 */
public class ProductMgr
{

	private static ProductMgr pm = null;

	static
	{
		if (null == pm)
		{
			pm = new ProductMgr();
			pm.setDao(new ProductMySQLDAO());
		}
	}

	private ProductMgr()
	{
	}

	/**
	 * @category 单例模式
	 * @return
	 */
	public static ProductMgr getInstance()
	{
		return pm;
	}

	ProductDAO dao = null;

	public ProductDAO getDao()
	{
		return dao;
	}

	public void setDao(ProductDAO dao)
	{
		this.dao = dao;
	}

	/**
	 * @category 获取商品列表
	 */
	public List<Product> getProducts()
	{
		return dao.getProducts();
	}

	/**
	 * @category 分页获取商品列表
	 */
	public List<Product> getProducts(int pageNo, int pageSize)
	{
		return dao.getProducts(pageNo, pageSize);
	}

	/**
	 * @category 分页获取商品列表,返回总页数
	 */
	public int getProducts(List<Product> products, int pageNo, int pageSize)
	{
		return dao.getProducts(products, pageNo, pageSize);
	}

	/**
	 * @category 商品搜索
	 */
	public int findProducts(List<Product> products, int[] categoryId, String keyword, double lowNormalPrice,
			double highNormalPrice, double lowMemberPrice, double highMemberPrice, Date startDate, Date endDate,
			int pageNo, int pageSize)
	{
		return dao.findProducts(products, categoryId, keyword, lowNormalPrice, highNormalPrice, lowMemberPrice,
				highMemberPrice, startDate, endDate, pageNo, pageSize);
	}

	/**
	 * @category 商品搜索
	 */
	public List<Product> findProducts(String name)
	{
		return null;
	}

	/**
	 * @category 删除商品
	 */
	public boolean deleteProductByCategoryId(int categoryId)
	{
		return false;
	}

	/**
	 * @category 批量删除商品
	 */
	public boolean deleteProductByCategoryId(int[] idArray)
	{
		return false;
	}

	/**
	 * @category 添加商品
	 */
	public boolean addProduct(Product p)
	{
		return dao.addProductProduct(p);
	}

	/**
	 * @category 根据id 查询商品信息
	 */
	public Product loadById(int id)
	{
		return dao.loadById(id);
	}

	/**
	 * @category 更改商品信息
	 */
	public boolean updateProduct(Product p)
	{
		return dao.updateProduct(p);
	}

	/**
	 * @category 获取最新产品
	 */
	public List<Product> getLateastProducts(int count)
	{
		return dao.getLateastProducts(count);
	}
}
