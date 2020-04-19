package com.heaven.service.impl;

import com.heaven.domain.User;
import com.heaven.service.UserService;
import org.springframework.stereotype.Component;

@Component//注入spring容器中
public class UserServiceFallback implements UserService {

    @Override
    public User findById(Integer id) {
        User user = new User();
        user.setName("用户信息不存在");
        return user;
    }
}
