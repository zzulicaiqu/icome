package cn.zzuli.cloud.user.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.zzuli.cloud.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class UserFrontController {

	@Autowired
	public UserService userService1;
	
	
	@ApiOperation(value = "根据Id获取信息",notes = "根据Id获取信息")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "学生编号的Id",required = true,dataType = "Integer")
	})
	@RequestMapping(value = "/find/{id}",method = RequestMethod.POST)
	public Object getById(@PathVariable("id") Integer id){
		return userService1.getById(id);
	}
}