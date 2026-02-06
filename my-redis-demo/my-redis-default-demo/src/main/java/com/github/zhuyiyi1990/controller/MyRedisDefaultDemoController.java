package com.github.zhuyiyi1990.controller;

import com.github.zhuyiyi1990.pojo.Product;
import com.github.zhuyiyi1990.pojo.User;
import com.github.zhuyiyi1990.service.OrderService;
import com.github.zhuyiyi1990.service.ProductService;
import com.github.zhuyiyi1990.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myRedisDemo/default")
@RequiredArgsConstructor
public class MyRedisDefaultDemoController {

    private final UserService userService;

    private final ProductService productService;

    private final OrderService orderService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable Long id) {
        // 第一次请求会查询数据库，第二次会从缓存获取
        return userService.getUserById(id);
    }

    @GetMapping("/userUnlessNull/{id}")
    public User getUserUnlessNull(@PathVariable Long id) {
        // 第一次请求会查询数据库，第二次会从缓存获取
        return userService.getUserUnlessNull(id);
    }

    @DeleteMapping("/user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "删除成功，缓存已清除";
    }

    @GetMapping("/products")
    public List<Product> getProducts(
            @RequestParam String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return productService.getProductsByCategory(category, page, size);
    }

}