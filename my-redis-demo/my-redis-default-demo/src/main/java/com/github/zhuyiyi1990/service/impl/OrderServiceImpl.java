package com.github.zhuyiyi1990.service.impl;

import com.github.zhuyiyi1990.pojo.Order;
import com.github.zhuyiyi1990.service.OrderService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    @Cacheable(value = "orders", key = "#orderId")
    public Order getOrder(Long orderId) {
        System.out.println("查询订单，ID：" + orderId);
        return new Order(orderId, "订单" + orderId, 100.00);
    }

    @Override
    @Cacheable(value = {"orders", "userOrders"}, key = "'user_' + #userId")
    public List<Order> getUserOrders(Long userId) {
        System.out.println("查询用户订单，用户ID：" + userId);
        return Arrays.asList(
                new Order(1L, "用户" + userId + "的订单1", 100.00),
                new Order(2L, "用户" + userId + "的订单2", 200.00)
        );
    }

    // 同时清除多个缓存
    @Override
    @CacheEvict(value = {"orders", "userOrders"}, allEntries = true)
    public void clearAllOrderCache() {
        System.out.println("清除所有订单缓存");
    }

    // 精确清除特定缓存
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "orders", key = "#orderId"),
                    @CacheEvict(value = "userOrders", key = "'user_' + #userId")
            }
    )
    public void deleteOrder(Long orderId, Long userId) {
        System.out.println("删除订单：" + orderId);
        // 执行删除操作
    }

}