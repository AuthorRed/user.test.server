package cn.author.tradewebtest.service;

import cn.author.tradewebtest.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface UserService {
	List<SysUser> list(@Param("params") Map<String, Object> params, @Param("offset") Integer page);
	SysUser getUser(String username);

	int save(SysUser user);

	int deleteUser(Long userId);

	SysUser getById(Long id);

	void changePassword(String username, String oldPassword, String newPassword);

}
