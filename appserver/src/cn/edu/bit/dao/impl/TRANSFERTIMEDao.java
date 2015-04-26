package cn.edu.bit.dao.impl;                                          
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import cn.edu.bit.dao.ITRANSFERTIMEDao;
import cn.edu.bit.entity.TRANSFERTIME;
                                                                            
@Repository("tRANSFERTIMEDao")                                                    
public class TRANSFERTIMEDao extends HibernateDaoSupport implements ITRANSFERTIMEDao{   
                                                                            
	@Resource
	public void setSessionFacotry(SessionFactory sessionFacotry) {
		super.setSessionFactory(sessionFacotry);
	}
	@Override                                                                 
	public boolean add(TRANSFERTIME entity) {                                       
		entity.setT_id(UUID.randomUUID().toString());                                                                      
		this.getHibernateTemplate().save(entity);                             
		                                                                      
		return true;                                                          
	}                                                                         
                                                                            
	@Override                                                                 
	public boolean delete(String id) {                                        
		                                                                      
		this.getHibernateTemplate().delete(this.getEntity(id));                                                                      
		return true;                                                         
	}                                                                         
	public boolean delete(TRANSFERTIME entity) {                                          
		                                                                      
		this.getHibernateTemplate().delete(entity);                                                                      
		return true;                                                         
	}                                                                         
                                                                            
	@Override                                                                 
	public boolean update(TRANSFERTIME entity) {                                    
		this.getHibernateTemplate().update(entity);                                                                      
		return true;                                                         
	}                                                                         
                                                                            
	@Override                                                                 
	public TRANSFERTIME getEntity(String id) {                                      
      List<TRANSFERTIME> list = getEntityList(" t_id='" + id+"'");            
      if (list.isEmpty())                                                      
      {
           return null;                                                   
      }
      return list.get(0);                                                         
		                                                                      
	}                                                                         
                                                                            
	@Override                                                                 
	public List<TRANSFERTIME> getEntityList(String swhere) {                        
		                                                                      
		Query query = this.getSession().createQuery(						
				"from TRANSFERTIME where " + swhere);                  
		                                                                    
		List<TRANSFERTIME> list =  (List<TRANSFERTIME>)query.list();  
                                                                          
		return list;                                                        
	}                                                                         
	@Override 																									
	//分页查询 																									
	public HashMap getPageList(int pageNo, int pageSize,                                          
			String orderBy, String swhere) {                                                                    
		Query query = this.getSession().createQuery("from TRANSFERTIME where "+swhere+" order by "+orderBy); 
		int total = query.list().size(); 
		query.setFirstResult(pageNo * pageSize);                                                            
		query.setMaxResults(pageSize);                                                                          
		                                                                                                        
		List<TRANSFERTIME> list = (List<TRANSFERTIME>)query.list();                                       
		HashMap hm = new HashMap();
		hm.put("data", list);
		hm.put("total", total);
		                                                                                                        
		return hm;                                                                                            
	}                                                                                                           
	//批量更新 
	public boolean updateOrSaveEntityList(List<TRANSFERTIME> list){    
		this.getHibernateTemplate().saveOrUpdateAll(list); 
		return true; 
	} 
}                                                                           
