package cn.edu.bit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bit.dao.IPush_MsgDao;
import cn.edu.bit.entity.PUSH_MSG;
import cn.edu.bit.service.IPush_MsgService;

@Service("push_MsgService")
public class Push_MsgService implements IPush_MsgService {

	private IPush_MsgDao push_MsgDao;

	public IPush_MsgDao getpush_MsgDao() {
		return push_MsgDao;
	}

	@Resource
	public void setpush_MsgDao(IPush_MsgDao push_MsgDao) {
		this.push_MsgDao = push_MsgDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean add(PUSH_MSG entity) {

		return push_MsgDao.add(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(String id) {

		return push_MsgDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(PUSH_MSG entity) {

		return push_MsgDao.update(entity);
	}

	@Override
	public PUSH_MSG getEntity(String id) {

		return push_MsgDao.getEntity(id);
	}

	@Override
	public List<PUSH_MSG> getEntityList(String swhere, Date lastDate) {

		return push_MsgDao.getEntityList(swhere, lastDate);
	}

	/*
	 * 分页查询 参数说明:pageNo页码：从1开始，代表第几页;pageSize:每页显示条数 ，orderBy：排序字段，如： createDate
	 * desc; swhere:过滤条件：如 employeeNo='19049' and last_name='田越'
	 */
	@Override
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {

		return push_MsgDao.getPageList(pageNo, pageSize, orderBy, swhere);

	}
}
