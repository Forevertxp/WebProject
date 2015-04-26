package cn.edu.bit.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.edu.bit.common.HelperPager;
import cn.edu.bit.dao.ICommonDao;
import cn.edu.bit.service.ICommonService;

@Service("commonService")
public class CommonService implements ICommonService {

	private ICommonDao commonDao;

	public ICommonDao getCommonDao() {
		return commonDao;
	}

	@Resource
	public void setCommonDao(ICommonDao commonDao) {
		this.commonDao = commonDao;
	}

	// 分页查询
	@Override
	public HashMap getPageList(HelperPager pager) {
		return commonDao.getPageList(pager);

	}

	@Override
	public int executeSql(String sql) {
		// TODO Auto-generated method stub
		return commonDao.executeSql(sql);
	}

	@Override
	public Object getSingleValue(String sql) {
		// TODO Auto-generated method stub
		return commonDao.getSingleValue(sql);
	}

	@Override
	public List getEntityList(String ViewORTableName, String swhere) {
		// TODO Auto-generated method stub

		return commonDao.getEntityList(ViewORTableName, swhere);
	}

	@Override
	public Date getCurrentDBServiceDate() {
		// TODO Auto-generated method stub
		return commonDao.getCurrentDBServiceDate();
	}
	
	@Override
	public List getAllValue(String sql) {
		// TODO Auto-generated method stub
		return commonDao.getAllValue(sql);
	}
}
