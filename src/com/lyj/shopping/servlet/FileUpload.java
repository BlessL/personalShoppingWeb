package com.lyj.shopping.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUpload extends HttpServlet
{
	private static final long serialVersionUID = 2837065445307996165L;

	public void destroy()
	{
		super.destroy();
	}

	public FileUpload()
	{
	}

	/**
	 * @category 文件上传
	 * @return true —— success; false —— fail.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		int id = -1;
		res.setContentType("text/html; charset=GB2312");
		PrintWriter out = res.getWriter();

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(4096);
		factory.setRepository(new File("d:\\temp\\"));
		ServletFileUpload upload = new ServletFileUpload(factory);

		// 设置允许用户上传文件大小,单位:字节
		upload.setSizeMax(1000000);
		try
		{
			List fileItems = upload.parseRequest(req);
			Iterator iter = fileItems.iterator();
			// 正则匹配，过滤路径取文件名
			String regExp = ".+\\\\(.+)$";
			// 过滤掉的文件类型
			String[] errorType = { ".exe", ".com", ".cgi", ".asp", ".php", ".jsp" };
			Pattern p = Pattern.compile(regExp);
			while (iter.hasNext())
			{
				FileItem item = (FileItem) iter.next();

				if (item.isFormField())
				{
					if (item.getFieldName().equals("id"))
					{
						id = Integer.parseInt(item.getString());
					}
				}
				// 忽略其他不是文件域的所有表单信息
				if (!item.isFormField())
				{
					String name = item.getName();
					long size = item.getSize();
					// 有多个文件域时，只上传有文件的
					if ((name == null || name.equals("")) && size == 0)
						continue;
					Matcher m = p.matcher(name);
					boolean result = m.find();
					if (result)
					{
						for (int temp = 0; temp < errorType.length; temp++)
						{
							if (m.group(1).endsWith(errorType[temp]))
							{
								throw new IOException(name + ": Wrong File Type");
							}
						}
						try
						{
							// 保存上传的文件到指定的目录
							// 在下文中上传文件至数据库时，将对这里改写
							// item.write(new File("d:\\" + m.group(1)));
							item.write(new File("d:\\temp\\" + id + ".jpg"));

							// 生成所有未生成的目录
							System.out.println(name + "&nbsp;&nbsp;" + size + "<br>");
						}
						catch (Exception e)
						{
							out.println(e);
						}
					}
					else
					{
						throw new IOException("fail to upload");
					}
				}
			}
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		catch (FileUploadException e)
		{
			System.out.println(e);
		}
	}

	/** 从路径中获取单独文件 */
	public String GetFileName(String filepath)
	{
		String returnstr = "*.*";
		int length = filepath.trim().length();
		filepath = filepath.replace('\\', '/');
		if (length > 0)
		{
			int i = filepath.lastIndexOf("/");
			if (i >= 0)
			{
				filepath = filepath.substring(i + 1);
				returnstr = filepath;
			}
		}
		return returnstr;
	}

	public void init() throws ServletException
	{

	}

}
