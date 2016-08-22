/**
 * 
 */
package com.lyj.shopping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yujin.liu
 *
 */
public class Category
{
	int id;
	String name;
	String descr;
	int pid;
	boolean leaf;
	int grade;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescr()
	{
		return descr;
	}

	public void setDescr(String descr)
	{
		this.descr = descr;
	}

	public int getPid()
	{
		return pid;
	}

	public void setPid(int pid)
	{
		this.pid = pid;
	}

	public boolean isLeaf()
	{
		return leaf;
	}

	public void setLeaf(boolean leaf)
	{
		this.leaf = leaf;
	}

	public int getGrade()
	{
		return grade;
	}

	public void setGrade(int grade)
	{
		this.grade = grade;
	}

	@Override
	public String toString()
	{
		return "Category [id=" + id + ", name=" + name + ", descr=" + descr + ", pid=" + pid + ", leaf=" + leaf
				+ ", grade=" + grade + "]";
	}

	/**
	 * @category 获取类别列表
	 */
	public static List<Category> getCategories()
	{
		List<Category> list = new ArrayList<Category>();
		CategoryDAO.getCategories(list, 0);
		return list;
	}

	/**
	 * @category 添加类别
	 */
	public static void add(Category c)
	{
		CategoryDAO.save(c);
	}

	/**
	 * @category 删除类别
	 */
	public void delete()
	{
		CategoryDAO.delete(this.id, this.pid);
	}

	/**
	 * @category 添加顶层类别
	 */
	public static void addTopCategory(String name, String descr)
	{
		addChildCategory(0, name, descr);
		/*
		 * Category c = new Category(); c.setId(-1); c.setName(name);
		 * c.setDescr(descr); c.setPid(0); c.setLeaf(true); c.setGrade(1);
		 * CategoryDAO.save(c);
		 */
	}

	/**
	 * @category 对外提供的接口
	 */
	public void addChildCategory(Category c)
	{
		addChildCategory(id, c.getName(), c.getDescr());
	}

	/**
	 * @category 添加子类别
	 */
	public static void addChildCategory(int pid, String name, String descr)
	{

		CategoryDAO.addChildCategory(pid, name, descr);

	}

	/**
	 * @category 通过id 查找category
	 */
	public static Category loadById(int id)
	{
		return CategoryDAO.loadById(id);
	}

	/**
	 * @category 获取子类别
	 */
	public List<Category> getChilds()
	{
		return CategoryDAO.getChilds(this);
	}

}
