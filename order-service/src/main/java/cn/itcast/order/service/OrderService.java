package cn.itcast.order.service;

import cn.itcast.feign.clients.UserClient;
import cn.itcast.feign.pojo.User;
import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Slf4j
@Service
@EnableFeignClients(basePackages = "cn.itcast.feign.clients")
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserClient userClient;
    //方式一：是用RestTemplate方式发起http请求
    /*
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
//        log.info(String.valueOf(order.getUserId()));
        //2、利用RestTemplate发起htt请求，查询用户
        String url="http://userservice/user/"+order.getUserId();
        //2.2发送http请求，实现远程调用
        User user=restTemplate.getForObject(url,User.class);
        //3、封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }*/

    //方式二：是用Feign方式发起http请求
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2.调用Feign远程调用
        User user = userClient.findById(order.getUserId());
        //3、封装user到order
        order.setUser(user);
        // 4.返回
        return order;
    }

}
