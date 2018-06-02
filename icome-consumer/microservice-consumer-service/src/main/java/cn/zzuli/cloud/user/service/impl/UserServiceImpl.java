package cn.zzuli.cloud.user.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zzuli.cloud.mapper.StudentMapper;
import cn.zzuli.cloud.model.Student;
import cn.zzuli.cloud.user.service.UserService;


/**
 * 
 * @author caiqu
 *
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	public StudentMapper studentMapperMapper;

	@Override
	public Student getById(Integer s) {
		
		return studentMapperMapper.selectByPrimaryKey(s);
	}
	

	
	
}
