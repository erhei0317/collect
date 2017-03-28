package com.candata.mappertest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.candata.coremodel.User;
import com.candata.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class UserMapperTest {
	@Autowired
	private UserService userService;
	

	@Test
	public void testMapper(){
		User user = userService.getUserByid(111);
		System.out.println(user.getEmail());
	}
}
