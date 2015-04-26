package cn.edu.bit.dao.impl;                                          
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.dao.IPushMsg_ReadLogDao;
import cn.edu.bit.entity.PUSHMSG_READLOG;
                                                                            
@Repository("pushMsg_ReadLogDao")                                                    
public class PushMsg_ReadLogDao extends HibernateDaoSupport implements IPushMsg_ReadLogDao{   
                                                                            
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	@Override                                                                 
	public boolean add(PUSHMSG_READLOG entity) {                                                                          
		this.getHibernateTemplate().save(entity);                             
		                                                                      
		return true;                                                          
	}                                                                         
                                                                            
	@Override                                                                 
	public boolean delete(String id) {                                        
		                                                                      
		this.getHibernateTemplate().delete(this.getEntity(id));                                                                      
		return true;                                                         
	}                                                                         
	public boolean delete(PUSHMSG_READLOG entity) {                                          
		                                                                      
		this.getHibernateTemplate().delete(entity);                                                                      
		return true;                                                         
	}                                                                         
                                                                            
	@Override                                                                 
	public boolean update(PUSHMSG_READLOG entity) {                                    
		this.getHibernateTemplate().update(entity);                                                                      
		return true;                                                         
	}                                                                         
                                                                            
	@Override                                                                 
	public PUSHMSG_READLOG getEntity(String id) {                                      
      List<PUSHMSG_READLOG> list = getEntityList(" r_id='" + id+"'");            
      if (list.isEmpty())                                                      
      {
           return null;                                                   
      }
      return list.get(0);                                                         
		                                                                      
	}                                                                         
                                                                            
	@Override                                                                 
	public List<PUSHMSG_READLOG> getEntityList(String swhere) {                        
		                                                                      
		Query query = this.getSession().createQuery(						
				"from PUSHMSG_READLOG where " + swhere);                  
		                                                                    
		List<PUSHMSG_READLOG> list =  (List<PUSHMSG_READLOG>)query.list();  
                                                                          
		return list;                                                        
	}                                                                         
	@Override 																									
	//分页查询 																									
	public HashMap getPageList(int pageNo, int pageSize,                                          
			String orderBy, String swhere) {                                                                    
		Query query = this.getSession().createQuery("from PUSHMSG_READLOG where "+swhere+" order by "+orderBy); 
		int total = query.list().size(); 
		query.setFirstResult(pageNo * pageSize);                                                            
		query.setMaxResults(pageSize);                                                                          
		                                                                                                        
		List<PUSHMSG_READLOG> list = (List<PUSHMSG_READLOG>)query.list();                                       
		HashMap hm = new HashMap();
		hm.put("data", list);
		hm.put("total", total);
		                                                                                                        
		return hm;                                                                                            
	}                                                                                                           
}                                                                           
