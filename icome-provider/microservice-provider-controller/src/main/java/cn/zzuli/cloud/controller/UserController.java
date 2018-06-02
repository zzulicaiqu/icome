package cn.zzuli.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.zzuli.cloud.commons.web.ActionResult;
import cn.zzuli.cloud.model.Student;
import cn.zzuli.cloud.user.service.UserService;

/**
 * 
 * @author 蔡渠
 * @date 2018年4月1日
 * @time 下午2:10:56
 * @title UserController.java
 */
@RestController
public class UserController {

	@Autowired
	public UserService studentService;
	
	
	@RequestMapping("/find/{id}")
	public ActionResult<Student> getById(Integer id){
		
		ActionResult<Student> ac = new ActionResult<>();
		ac.setCode(200);
		Student byId = studentService.getById(id);
		ac.setData(byId);
		ac.setMessage("11111111111111");
		return ac;
		
	}
	
	
}
