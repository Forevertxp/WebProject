package cn.edu.bit.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.edu.bit.entity.Consult;

public interface IConsultService {

	// 增加对象
	public boolean add(Consult entity);

	// 删除对象
	public boolean delete(String id);

	// 修改对象
	public boolean update(Consult entity);

	// 根据主键获得对象
	public Consult getEntity(String id);

	// 根据查询条件获得对象列表
	public List<Consult> getEntityList(String swhere, Date lastTime);
	
	List<Consult> getEntityList(String swhere);

	// 分页查询
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere);

	// 批量更新
	public boolean updateOrSaveEntityList(List<Consult> list);
}
