package cn.edu.bit.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.dao.IConsultDao;
import cn.edu.bit.entity.Consult;

@Repository("consultDao")
public class ConsultDao extends HibernateDaoSupport implements IConsultDao {

	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}

	@Override
	public boolean add(Consult entity) {
		 
		this.getHibernateTemplate().save(entity);

		return true;
	}

	@Override
	public boolean delete(String id) {

		this.getHibernateTemplate().delete(this.getEntity(id));
		return true;
	}

	public boolean delete(Consult entity) {

		this.getHibernateTemplate().delete(entity);
		return true;
	}

	@Override
	public boolean update(Consult entity) {
		this.getHibernateTemplate().update(entity);
		return true;
	}

	@Override
	public Consult getEntity(String id) {
		List<Consult> list = getEntityList(" c_id='" + id + "'");
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);

	}

	@Override
	public List<Consult> getEntityList(String swhere) {

		Query query = this.getSession().createQuery(
				"from Consult where " + swhere);

		List<Consult> list = (List<Consult>) query.list();

		return list;
	}

	@Override
	// 分页查询
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {
		Query query = this.getSession().createQuery(
				"from Consult where " + swhere + " order by " + orderBy);
		int total = query.list().size();
		query.setFirstResult(pageNo * pageSize);
		query.setMaxResults(pageSize);

		List<Consult> list = (List<Consult>) query.list();
		HashMap hm = new HashMap();
		hm.put("data", list);
		hm.put("total", total);

		return hm;
	}

	@Override
	public boolean updateOrSaveEntityList(List<Consult> list) {

		this.getHibernateTemplate().saveOrUpdateAll(list);

		return true;
	}

	@Override
	public List<Consult> getEntityList(String swhere, Date lastDate) {
		// TODO Auto-generated method stub

		String sql = "from Consult where STATUS=1 and " + swhere;

		Query query = null;
		if (lastDate != null) {

			Date sqlDate = null;
			try {

				sqlDate = new java.sql.Date(lastDate.getTime());

			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}

			query = this.getSession().createQuery(sql);
			query.setTimestamp("lastTranTime", sqlDate);

		} else {
			query = this.getSession().createQuery(sql);
		}

		List<Consult> list = (List<Consult>) query.list();

		return list;

	}
}
