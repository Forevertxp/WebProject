package cn.edu.bit.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.common.HelperPager;
import cn.edu.bit.common.HelperString;
import cn.edu.bit.dao.ICommonDao;

@Repository("commonDao")
public class CommonDao extends HibernateDaoSupport implements ICommonDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	/**
	 * 获取数据库当前时间
	 */
	public Date getCurrentDBServiceDate() {

		try {

			Object obj = this
					.getSession()
					.createSQLQuery(
							"select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') aa  from dual")
					.list().get(0);

			SimpleDateFormat formatDate = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");

			return formatDate.parse(obj.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// java.util.Date date = new java.util.Date(sqlDate.getTime());

		return null;

	}

	/**
	 * 执行原生态SQL语句，返回影响条数
	 * 
	 * @param sql
	 * @return
	 */
	@Override
	public int executeSql(String sql) {

		return this.getSession().createSQLQuery(sql).executeUpdate();
	}

	/**
	 * 分页查询
	 */
	@Override
	public HashMap getPageList(HelperPager pager) {

		HashMap hm = new HashMap();
		try {
			Class<?> cla = Class.forName("cn.edu.bit.entity."
					+ pager.getEntityName().toUpperCase());

			// 统计总数量
			int total = Integer.valueOf(this.getSession()
					.createSQLQuery(pager.getTotalSql()).list().get(0)
					.toString());

			SQLQuery query = this.getSession().createSQLQuery(pager.getSql())
					.addEntity(cla);

			query.setFirstResult(pager.getPageIndex() * pager.getPageSize());
			query.setMaxResults(pager.getPageSize());

			List list = query.list();

			hm.put("data", list);
			hm.put("total", total);

			return hm;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hm;
	}

	/***
	 * 从数据库获得唯一值。
	 * 
	 * @param sql
	 * @return
	 */
	@Override
	public Object getSingleValue(String sql) {
		if (!HelperString.isNullOrEmpty(sql.trim())) {
			List list = this.getSession().createSQLQuery(sql.trim()).list();
			if (list != null && list.size() == 1)
				// return this.getSession().createSQLQuery(sql).list().get(0);
				return list.get(0);
			else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * 动态查询，ViewORTableName是javaBean的名称，
	 * 
	 * @param ViewORTableName
	 * @param swhere
	 * @return
	 */
	@Override
	public List getEntityList(String ViewORTableName, String swhere) {

		String sql = "select * from " + ViewORTableName;

		if (!HelperString.isNullOrEmpty(swhere.trim())) {
			sql += " where " + swhere;
		}
		try {
			Class<?> cla = Class.forName("cn.edu.bit.entity."
					+ ViewORTableName.toUpperCase());

			SQLQuery query = this.getSession().createSQLQuery(sql)
					.addEntity(cla);

			return query.list();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/***
	 * 从数据库获得唯一值。
	 * 
	 * @param sql
	 * @return
	 */
	@Override
	public List getAllValue(String sql) {
		if (!HelperString.isNullOrEmpty(sql.trim())) {
			List list = this.getSession().createSQLQuery(sql.trim()).list();
			return list;
		} else {
			return null;
		}
	}

}
