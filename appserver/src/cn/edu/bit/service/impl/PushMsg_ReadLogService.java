package cn.edu.bit.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bit.dao.IPushMsg_ReadLogDao;
import cn.edu.bit.entity.PUSHMSG_READLOG;
import cn.edu.bit.service.IPushMsg_ReadLogService;

@Service("pushMsg_ReadLogService")
public class PushMsg_ReadLogService implements IPushMsg_ReadLogService {

	private IPushMsg_ReadLogDao pushMsg_ReadLogDao;

	public IPushMsg_ReadLogDao getpushMsg_ReadLogDao() {
		return pushMsg_ReadLogDao;
	}

	@Resource
	public void setpushMsg_ReadLogDao(IPushMsg_ReadLogDao pushMsg_ReadLogDao) {
		this.pushMsg_ReadLogDao = pushMsg_ReadLogDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean add(PUSHMSG_READLOG entity) {

		return pushMsg_ReadLogDao.add(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(String id) {

		return pushMsg_ReadLogDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(PUSHMSG_READLOG entity) {

		return pushMsg_ReadLogDao.update(entity);
	}

	@Override
	public PUSHMSG_READLOG getEntity(String id) {

		return pushMsg_ReadLogDao.getEntity(id);
	}

	@Override
	public List<PUSHMSG_READLOG> getEntityList(String swhere) {

		return pushMsg_ReadLogDao.getEntityList(swhere);
	}

	/*
	 * 分页查询 参数说明:pageNo页码：从1开始，代表第几页;pageSize:每页显示条数 ，orderBy：排序字段，如： createDate
	 * desc; swhere:过滤条件：如 employeeNo='19049' and last_name='田越'
	 */
	@Override
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {

		return pushMsg_ReadLogDao
				.getPageList(pageNo, pageSize, orderBy, swhere);

	}
}
