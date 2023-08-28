package com.github.xjs.web;

import com.github.xjs.feign.UserClient;
import com.github.xjs.pojo.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private UserClient userClient;

    @Transactional
    @GetMapping("/{id}")
    public Order queryById(@PathVariable("id") Long id) {
        Order order = new Order(id, "测试商品", "100", id, userClient.queryById(id));
        return order;
    }

    @Transactional
    public void f1(){
        this.f2();
    }

    @Transactional
    public void f2(){
        throw new RuntimeException();
    }

    public static class A{
        @Autowired
        B b;
        @Transactional(rollbackFor = Exception.class)
        public void a(){
            try{
                b.b();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    public static class B{
        @Transactional
        public void b(){
            throw new RuntimeException();
        }
    }


}
