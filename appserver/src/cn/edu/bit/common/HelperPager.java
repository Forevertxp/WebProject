package cn.edu.bit.common;

import javax.servlet.http.HttpServletRequest;

public class HelperPager {

	private int pageSize;
	private int pageIndex;
	private String sort;
	public String swhere;
	private String sql;
	private String entityName;
	private String totalSql;
	private String hSql;
	private Object[] params;

	/**
	 * HSQL的sql语句
	 * 
	 * @return
	 */
	public String gethSql() {

		this.hSql = "from " + entityName;

		if (!HelperString.isNullOrEmpty(swhere)) {
			this.hSql += " where " + swhere;
		}
		if (!HelperString.isNullOrEmpty(sort)) {
			this.hSql += " order by " + sort;
		}
		return this.hSql;

	}

	/**
	 * 统计总条数SQL
	 * 
	 * @return
	 */
	public String getTotalSql() {
		this.totalSql = "select count(1) from " + entityName;
		if (!HelperString.isNullOrEmpty(swhere)) {
			this.totalSql += " where " + swhere;
		}
		return totalSql;
	}

	/**
	 * 原生SQL语句
	 * 
	 * @return
	 */
	public String getSql() {
		this.sql = "select * from " + entityName;

		if (!HelperString.isNullOrEmpty(swhere)) {
			this.sql += " where " + swhere;
		}
		if (!HelperString.isNullOrEmpty(sort)) {
			this.sql += " order by " + sort;
		}
		return this.sql;
	}

	/**
	 * javaBean的名称
	 * 
	 * @return
	 */
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 每页条数
	 * 
	 * @return
	 */
	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 当前页码
	 * 
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 排序字段和方法
	 * 
	 * @return
	 */
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public HelperPager() {
	}

	public HelperPager(int pageIndex, int pageSize, String sort) {

		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.sort = sort;
	}

	public HelperPager(HttpServletRequest request, String entityNm) {
		swhere = "";
		this.entityName = entityNm;
		if (request != null && request.getParameter("pageIndex") != null) {

			this.pageIndex = Integer.valueOf(request.getParameter("pageIndex"));
			this.pageSize = Integer.valueOf(request.getParameter("pageSize"));
			String sortOrder = request.getParameter("sortOrder");
			this.sort = request.getParameter("sortField");
			if (!HelperString.isNullOrEmpty(this.sort)
					&& !HelperString.isNullOrEmpty(sortOrder)) {
				this.sort += " " + sortOrder;
			}
		} else {
			this.pageIndex = 0;
			this.pageSize = 15; /*Integer.valueOf(HelperProperties
					.getProperty("pager.pagesize")); */
			this.sort = "";

		}
	}
	
	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
