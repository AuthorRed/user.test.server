package cn.author.tradewebtest.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.author.tradewebtest.entity.SysUser;
import cn.author.tradewebtest.dao.UserDao;
import cn.author.tradewebtest.service.UserService;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger("adminLogger");



	@Autowired
	private UserDao userDao;
	@Override
	public List<SysUser> list(Map<String, Object> params, Integer page) {
		int count = userDao.count(params);
		List<SysUser> list =null;
		if(count>0){
			if (page<0){
				page=1;
			}
			int start = page*10;
			int end = start+10;
			list = userDao.list(params, start, end);
		}
		return list;
	}
	@Override
	public SysUser getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public int save(SysUser user) {
		return userDao.save(user);
	}

	@Override
	public int deleteUser(Long userId) {
		return userDao.deleteUser(userId);
	}

	@Override
	public SysUser getUser(String username) {
		return userDao.getUser(username);
	}

	@Override
	public void changePassword(String username, String oldPassword, String newPassword) {
		SysUser u = userDao.getUser(username);
		if (u == null) {
			throw new IllegalArgumentException("用户不存在");
		}
		userDao.changePassword(u.getId(),newPassword);

		log.debug("修改{}的密码", username);
	}


}
