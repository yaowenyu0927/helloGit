package com.ausware.yao;

import com.ausware.yao.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringbootRabbitmqApplicationTests {


    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        orderService.makeOrder("1001", "00001", 10);
    }

    @Test
    void test() {
        orderService.makeOrderDirect("1001", "00001", 10);
    }

    @Test
    void test2() {
        orderService.makeOrderTopic("1001", "00001", 10);
    }


    @Test
    void test3() {
        orderService.makeOrderTTL("1001", "00001", 10);
    }


    @Test
    void test4() {
        orderService.makeOrderTTLMessage("1001", "00001", 10);
    }

}
