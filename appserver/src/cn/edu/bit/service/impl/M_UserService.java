package cn.edu.bit.service.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.bit.common.HelperPager;
import cn.edu.bit.dao.IM_UserDao;
import cn.edu.bit.entity.M_User;
import cn.edu.bit.service.IM_UserService;

@Service("m_UserService")
public class M_UserService implements IM_UserService {

	private IM_UserDao m_UserDao;

	public IM_UserDao getm_UserDao() {
		return m_UserDao;
	}

	@Resource
	public void setm_UserDao(IM_UserDao m_UserDao) {
		this.m_UserDao = m_UserDao;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean add(M_User entity) {

		return m_UserDao.add(entity);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean delete(String id) {

		return m_UserDao.delete(id);
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean update(M_User entity) {

		return m_UserDao.update(entity);
	}

	@Override
	public M_User getEntity(String id) {

		return m_UserDao.getEntity(id);
	}

	@Override
	public List<M_User> getEntityList(String swhere) {

		return m_UserDao.getEntityList(swhere);
	}

	/*
	 * 分页查询 参数说明:pageNo页码：从1开始，代表第几页;pageSize:每页显示条数 ，orderBy：排序字段，如： createDate
	 * desc; swhere:过滤条件：如 employeeNo='19049' and last_name='田越'
	 */
	@Override
	// @Cacheable(value = "logCache", key = "#type+'-getPageList'")
	public HashMap getPageList(HelperPager pager) {

		return m_UserDao.getPageList(pager);

	}

	@CacheEvict(value = "logCache", key = "#type+'-getPageList'")
	public void flushPageList(HelperPager pager) {
		// 更新日志.
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean updateOrSaveEntityList(List<M_User> list) {

		return m_UserDao.updateOrSaveEntityList(list);
	}
}
