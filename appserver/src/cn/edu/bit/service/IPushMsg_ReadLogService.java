package cn.edu.bit.service;                       
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.entity.PUSHMSG_READLOG;
                                                    
public interface IPushMsg_ReadLogService {                       
                                                    
	//增加对象                                         
	public boolean add(PUSHMSG_READLOG entity);                 
	//删除对象                                         
	public boolean delete(String id);                  
	//修改对象                                         
	public boolean update(PUSHMSG_READLOG entity);              
	//根据主键获得对象                                 
	public PUSHMSG_READLOG getEntity(String id);                
	//根据查询条件获得对象列表                         
	public List<PUSHMSG_READLOG> getEntityList(String swhere);  
	//分页查询                         
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,String swhere);  
}                                                   
                                                    
