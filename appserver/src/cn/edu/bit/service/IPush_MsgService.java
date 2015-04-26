package cn.edu.bit.service;                       
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.entity.PUSH_MSG;
                                                    
public interface IPush_MsgService {                       
                                                    
	//增加对象                                         
	public boolean add(PUSH_MSG entity);                 
	//删除对象                                         
	public boolean delete(String id);                  
	//修改对象                                         
	public boolean update(PUSH_MSG entity);              
	//根据主键获得对象                                 
	public PUSH_MSG getEntity(String id);                
	//根据查询条件获得对象列表                         
	public List<PUSH_MSG> getEntityList(String swhere, Date lastDate);  
	//分页查询                         
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,String swhere);  
}                                                   
                                                    
