package com.github.zhuyiyi1990.service.impl;

import com.github.zhuyiyi1990.pojo.User;
import com.github.zhuyiyi1990.service.UserService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserServiceImpl implements UserService {

    // 基本用法：根据id缓存用户信息，缓存名称为"user"
    @Override
    @Cacheable(value = "user", key = "#id")
    public User getUserById(Long id) {
        // 模拟从数据库查询
        System.out.println("查询数据库，用户ID：" + id);
        return new User(id, "用户" + id, 18, new BigDecimal("5.43"), "男", "user@example.com");
    }

    // 使用 condition 条件判断
    @Override
    @Cacheable(value = "user", key = "#id", condition = "#id > 10")
    public User getUserWithCondition(Long id) {
        // 只有当id大于10时才缓存
        System.out.println("查询数据库，用户ID：" + id);
        return new User(id, "用户" + id, 18, new BigDecimal("5.43"), "男", "user@example.com");
    }

    // 使用 unless 排除某些结果
    @Override
    @Cacheable(value = "user", key = "#id", unless = "#result == null")
    public User getUserUnlessNull(Long id) {
        // 如果返回结果为null则不缓存
        System.out.println("查询数据库，用户ID：" + id);
        if (id == 0) {
            return null;
        }
        return new User(id, "用户" + id, 18, new BigDecimal("5.43"), "男", "user@example.com");
    }

    // 删除指定key的缓存
    @Override
    @CacheEvict(value = "user", key = "#id")
    public void deleteUser(Long id) {
        System.out.println("删除用户，ID：" + id);
        // 执行删除数据库操作
    }

    // 删除整个缓存名称下的所有缓存
    @Override
    @CacheEvict(value = "user", allEntries = true)
    public void clearAllUserCache() {
        System.out.println("清除所有用户缓存");
    }

    // 先执行方法，然后清除缓存（默认是方法执行后清除）
    @Override
    @CacheEvict(value = "user", key = "#user.id", beforeInvocation = false)
    public void updateUser(User user) {
        System.out.println("更新用户信息，ID：" + user.getId());
        // 执行更新操作
    }

    // 方法执行前清除缓存
    @Override
    @CacheEvict(value = "user", key = "#user.id", beforeInvocation = true)
    public void updateUserBefore(User user) {
        System.out.println("更新用户信息前清除缓存，ID：" + user.getId());
        // 执行更新操作
    }

}