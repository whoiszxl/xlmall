package com.zxlvoid.mybatis.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zxlvoid.dao.UserMapper;
import com.zxlvoid.pojo.User;

public class MyBatisTest {
	
	
	@Autowired
	private UserMapper userMapper;
	
	
	
	
	@Test
	public void testName() throws Exception {
		User selectByPrimaryKey = userMapper.selectByPrimaryKey(1);
		System.out.println(selectByPrimaryKey.toString());
	}
	
}
