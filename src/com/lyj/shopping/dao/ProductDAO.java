package com.lyj.shopping.dao;

import java.util.Date;
import java.util.List;

import com.lyj.shopping.Product;

/**
 * @author yujin.liu
 *
 */
public interface ProductDAO
{
	/**
	 * @category 获取商品列表
	 */
	public List<Product> getProducts();

	/**
	 * @category 分页获取商品列表
	 */
	public List<Product> getProducts(int pageNo, int pageSize);

	/**
	 * @category 分页获取商品，并返回总页数
	 */
	public int getProducts(List<Product> products, int pageNo, int pageSize);

	/**
	 * @category 商品搜索
	 */
	public int findProducts(List<Product> products, int[] categoryId, String keyword, double lowNormalPrice,
			double highNormalPrice, double lowMemberPrice, double highMemberPrice, Date startDate, Date endDate,
			int pageNo, int pageSize);

	/**
	 * @category 商品搜索
	 */
	public List<Product> findProducts(String name);

	/**
	 * @category 删除商品
	 */
	public boolean deleteProductByCategoryId(int categoryId);

	/**
	 * @category 批量删除商品
	 */
	public boolean deleteProductByCategoryId(int[] idArray);

	/**
	 * @category 更新商品
	 */
	public boolean updateProduct(Product p);

	/**
	 * @category 添加商品
	 */
	public boolean addProductProduct(Product p);

	/**
	 * @category 根据id 查询商品信息
	 */
	public Product loadById(int id);

	/**
	 * @category 获取最新产品信息
	 */
	public List<Product> getLateastProducts(int count);
}
