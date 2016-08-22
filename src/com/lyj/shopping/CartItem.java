package com.lyj.shopping;

/**
 * @author yujin.liu
 */
public class CartItem
{
	int productId;
	int count;
	double price;

	public int getProductId()
	{
		return productId;
	}

	public void setProductId(int productId)
	{
		this.productId = productId;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public double getTotalPrice()
	{
		return price * count;
	}

}
