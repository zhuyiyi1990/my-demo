package com.github.zhuyiyi1990.service;

import com.github.zhuyiyi1990.pojo.Order;

import java.util.List;

public interface OrderService {

    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);

    void clearAllOrderCache();

    void deleteOrder(Long orderId, Long userId);

}