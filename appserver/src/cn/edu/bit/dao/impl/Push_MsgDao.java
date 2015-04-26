package cn.edu.bit.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.dao.IPush_MsgDao;
import cn.edu.bit.entity.PUSH_MSG;

@Repository("push_MsgDao")
public class Push_MsgDao extends HibernateDaoSupport implements IPush_MsgDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public boolean add(PUSH_MSG entity) {
		entity.setP_id(UUID.randomUUID().toString());
		this.getHibernateTemplate().save(entity);

		return true;
	}

	@Override
	public boolean delete(String id) {

		this.getHibernateTemplate().delete(this.getEntity(id));
		return true;
	}

	public boolean delete(PUSH_MSG entity) {

		this.getHibernateTemplate().delete(entity);
		return true;
	}

	@Override
	public boolean update(PUSH_MSG entity) {
		this.getHibernateTemplate().update(entity);
		return true;
	}

	@Override
	public PUSH_MSG getEntity(String id) {
		List<PUSH_MSG> list = getEntityList(" p_id='" + id + "'");
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	public List<PUSH_MSG> getEntityList(String swhere) {

		Query query = this.getSession().createQuery(
				"from PUSH_MSG where " + swhere);

		List<PUSH_MSG> list = (List<PUSH_MSG>) query.list();

		return list;
	}

	@Override
	public List<PUSH_MSG> getEntityList(String swhere, Date lastDate) {

		String sql = "from PUSH_MSG where STATUS=1 ";
		Query query = null;
		if (lastDate != null && swhere != "") {

			Date sqlDate = null;
			try {
				sqlDate = new java.sql.Date(lastDate.getTime());

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
			sql += " and " + swhere;
			query = this.getSession().createQuery(sql);
			query.setTimestamp("lastTranTime", sqlDate);

		} else {
			query = this.getSession().createQuery(sql);
		}

		List<PUSH_MSG> list = (List<PUSH_MSG>) query.list();

		return list;
	}

	@Override
	// 分页查询
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {

		Query query = this.getSession().createQuery(
				"from PUSH_MSG where " + swhere + " order by " + orderBy);
		int total = query.list().size();
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);

		List<PUSH_MSG> list = (List<PUSH_MSG>) query.list();
		HashMap hm = new HashMap();
		hm.put("data", list);
		hm.put("total", total);

		return hm;
	}
}
