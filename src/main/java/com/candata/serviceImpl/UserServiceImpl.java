package com.candata.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.candata.coremodel.User;
import com.candata.dao.UserMapper;
import com.candata.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User getUserByid(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

}
