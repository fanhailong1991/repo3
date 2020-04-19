package com.heaven.controller;

import com.heaven.domain.User;
import com.heaven.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@DefaultProperties(defaultFallback = "consumerDefaultfallback")//全局的服务降级
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DiscoveryClient discoveryClient;//服务注册中心客户端对象,存储了服务的访问地址

    @Autowired
    UserService userService;

    @RequestMapping("/consumer/{id}")
//    @HystrixCommand(fallbackMethod = "hellofallback")
    @HystrixCommand//不指定，就走默认全局的服务降级方法
    public String hello(@PathVariable("id") Integer id) {
        String url = "http://privoder-service/user/findById?id=" + id;
        String jsonResult = restTemplate.getForObject(url, String.class);
        return jsonResult;
    }

    @RequestMapping("/feignconsumer/{id}")
    public User helloFeign(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    public String hellofallback(Integer id) {
        return "服务降级，请稍后再试";
    }

    public String consumerDefaultfallback(){
        return "全局默认，您好，非常抱歉，服务正忙，请您稍后尝试！！！";
    }

}
