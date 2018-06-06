package cn.zzuli.cloud.user.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("microservice-consumer-service")
public interface UserService {

	@RequestMapping(value = "/find/{id}",method = RequestMethod.POST)
	Object getById(@PathVariable Integer id);
}