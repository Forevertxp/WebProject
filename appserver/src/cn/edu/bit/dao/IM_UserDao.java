package cn.edu.bit.dao;                       
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.common.HelperPager;
import cn.edu.bit.entity.M_User;
                                                    
public interface IM_UserDao {                       
                                                    
	//增加对象                                         
	public boolean add(M_User entity);                 
	//删除对象                                         
	public boolean delete(String id);                  
	//删除对象                                         
	public boolean delete(M_User entity);                
	//修改对象                                         
	public boolean update(M_User entity);              
	//根据主键获得对象                                 
	public M_User getEntity(String id);                
	//根据查询条件获得对象列表                         
	public List<M_User> getEntityList(String swhere);  
	//分页查询                         
	public HashMap getPageList(HelperPager pager); 
	//批量更新
	public boolean updateOrSaveEntityList(List<M_User> list);
}                                                   
                                                    
