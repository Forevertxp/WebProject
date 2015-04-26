package cn.edu.bit.dao;                       
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.entity.TRANSFERTIME;
                                                    
public interface ITRANSFERTIMEDao {                       
                                                    
	//增加对象                                         
	public boolean add(TRANSFERTIME entity);                 
	//删除对象                                         
	public boolean delete(String id);                  
	//删除对象                                         
	public boolean delete(TRANSFERTIME entity);                
	//修改对象                                         
	public boolean update(TRANSFERTIME entity);              
	//根据主键获得对象                                 
	public TRANSFERTIME getEntity(String id);                
	//根据查询条件获得对象列表                         
	public List<TRANSFERTIME> getEntityList(String swhere);  
	//分页查询                         
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,String swhere);  
	//批量更新 
	public boolean updateOrSaveEntityList(List<TRANSFERTIME> list); 
}                                                   
                                                    
