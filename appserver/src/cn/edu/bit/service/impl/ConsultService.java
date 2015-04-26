package cn.edu.bit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bit.dao.IConsultDao;
import cn.edu.bit.entity.Consult;
import cn.edu.bit.service.IConsultService;
 
@Service("consultService")
public class ConsultService implements IConsultService {

	private IConsultDao consultDao;

	public IConsultDao getconsultDao() {
		return consultDao;
	}

	@Resource
	public void setconsultDao(IConsultDao consultDao) {
		this.consultDao = consultDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean add(Consult entity) {

		return consultDao.add(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(String id) {

		return consultDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(Consult entity) {

		return consultDao.update(entity);
	}

	@Override 
	public Consult getEntity(String id) {

		return consultDao.getEntity(id);
	}

	@Override 
	public List<Consult> getEntityList(String swhere) {

		return consultDao.getEntityList(swhere);
	}
	@Override 
	public List<Consult> getEntityList(String swhere,Date lastTime) {

		return consultDao.getEntityList(swhere,lastTime);
	}

	/*
	 * 分页查询 参数说明:pageNo页码：从1开始，代表第几页;pageSize:每页显示条数 ，orderBy：排序字段，如： createDate
	 * desc; swhere:过滤条件：如 employeeNo='19049' and last_name='田越'
	 */
	@Override 
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {

		return consultDao.getPageList(pageNo, pageSize, orderBy, swhere);

	}

	@Override
	public boolean updateOrSaveEntityList(List<Consult> list) {

		return consultDao.updateOrSaveEntityList(list);
	}
}
