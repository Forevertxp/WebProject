package cn.edu.bit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.common.HelperPager;

public interface ICommonService {

	/**
	 * 获取数据库当前时间
	 */
	public Date getCurrentDBServiceDate();
	/**
	 * 分页查询
	 * @param pager
	 * @return
	 */
	public HashMap getPageList(HelperPager pager);
	/**
	 * 执行SQL语句：增删改原生SQL语句
	 * @param sql
	 * @return
	 */
	public int executeSql(String sql);
	
	/***
	 * 从数据库获得唯一值。
	 * @param sql
	 * @return
	 */
	public Object getSingleValue(String sql);
	
	/**
	 * 动态查询，ViewORTableName是javaBean的名称，
	 * @param ViewORTableName
	 * @param swhere
	 * @return
	 */
	public List getEntityList(String ViewORTableName, String swhere);
	
	/***
	 * 从数据库获得所有记录。
	 * @param sql
	 * @return
	 */
	public List getAllValue(String sql);
}
