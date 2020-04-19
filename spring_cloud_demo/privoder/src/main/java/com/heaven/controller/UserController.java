package com.heaven.controller;

import com.heaven.domain.User;
import com.heaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@RefreshScope//刷新配置
public class UserController {

    @Value("${server.port}")
    private String port;

    @Value("${test.hello}")
    private String name;

    @Autowired
    UserService userService;

    @RequestMapping("/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping("/findById")
    public User findById(Integer id) {
        System.out.println("服务【" + port + "】被调用");
        User user = userService.findById(id);
        user.setName(name);
        user.setNote("服务【" + port + "】被调用");
        return user;
    }
}
