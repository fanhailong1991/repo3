package com.heaven.service;

import com.heaven.config.FeignConfiguration;
import com.heaven.domain.User;
import com.heaven.service.impl.UserServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "privoder-service",//指定feign调用的服务
        fallback = UserServiceFallback.class,//服务降级处理类
        configuration = FeignConfiguration.class)//日志级别配置
public interface UserService {

    @RequestMapping("/user/findById")
    User findById(@RequestParam("id") Integer id);

}
