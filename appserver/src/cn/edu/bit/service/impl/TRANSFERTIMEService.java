package cn.edu.bit.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bit.dao.ITRANSFERTIMEDao;
import cn.edu.bit.entity.TRANSFERTIME;
import cn.edu.bit.service.ITRANSFERTIMEService;

@Service("tRANSFERTIMEService")
public class TRANSFERTIMEService implements ITRANSFERTIMEService {

	private ITRANSFERTIMEDao tRANSFERTIMEDao;

	public ITRANSFERTIMEDao gettRANSFERTIMEDao() {
		return tRANSFERTIMEDao;
	}

	@Resource
	public void settRANSFERTIMEDao(ITRANSFERTIMEDao tRANSFERTIMEDao) {
		this.tRANSFERTIMEDao = tRANSFERTIMEDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean add(TRANSFERTIME entity) {

		return tRANSFERTIMEDao.add(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(String id) {

		return tRANSFERTIMEDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(TRANSFERTIME entity) {

		return tRANSFERTIMEDao.update(entity);
	}

	@Override
	public TRANSFERTIME getEntity(String id) {

		return tRANSFERTIMEDao.getEntity(id);
	}

	@Override
	public List<TRANSFERTIME> getEntityList(String swhere) {

		return tRANSFERTIMEDao.getEntityList(swhere);
	}

	/*
	 * 分页查询 参数说明:pageNo页码：从1开始，代表第几页;pageSize:每页显示条数 ，orderBy：排序字段，如： createDate
	 * desc; swhere:过滤条件：如 employeeNo='19049' and last_name='田越'
	 */
	@Override
	public HashMap getPageList(int pageNo, int pageSize, String orderBy,
			String swhere) {

		return tRANSFERTIMEDao.getPageList(pageNo, pageSize, orderBy, swhere);

	}

	@Override
	public boolean updateOrSaveEntityList(List<TRANSFERTIME> list) {

		return tRANSFERTIMEDao.updateOrSaveEntityList(list);
	}
}
