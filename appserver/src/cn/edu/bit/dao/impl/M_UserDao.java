package cn.edu.bit.dao.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.common.HelperPager;
import cn.edu.bit.dao.IM_UserDao;
import cn.edu.bit.entity.M_User;

@Repository("m_UserDao")
public class M_UserDao extends HibernateDaoSupport implements IM_UserDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public boolean add(M_User entity) {
		 
		this.getHibernateTemplate().save(entity);

		return true;
	}

	@Override
	public boolean delete(String id) {

		this.getHibernateTemplate().delete(this.getEntity(id));
		return true;
	}

	public boolean delete(M_User entity) {

		this.getHibernateTemplate().delete(entity);
		return true;
	}

	@Override
	public boolean update(M_User entity) {
		this.getHibernateTemplate().update(entity);

		return true;
	}

	@Override
	public M_User getEntity(String id) {
		List<M_User> list = getEntityList(" u_id='" + id + "'");
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	@Override
	public List<M_User> getEntityList(String swhere) {

		Query query = this.getSession().createQuery(
				"from M_User where " + swhere);

		List<M_User> list = (List<M_User>) query.list();

		return list;
	}

	@Override
	// 分页查询
	public HashMap getPageList(HelperPager pager) {
		 

		Query query = this.getSession().createQuery(pager.gethSql());
		if(pager.getParams()!=null){
			Object[] params=pager.getParams();
			for(int i=0;i<params.length;i++){
				query.setParameter(i, params[i]);
			}
		}
		
		
		int total = query.list().size();
		query.setFirstResult(pager.getPageIndex() * pager.getPageSize());
		query.setMaxResults(pager.getPageSize());
		
		
		List<M_User> list = (List<M_User>) query.list();
		HashMap hm = new HashMap();
		hm.put("data", list);
		hm.put("total", total);

		return hm;
	}

	@Override
	public boolean updateOrSaveEntityList(List<M_User> list) {

		this.getHibernateTemplate().saveOrUpdateAll(list);

		return true;
	}
}
