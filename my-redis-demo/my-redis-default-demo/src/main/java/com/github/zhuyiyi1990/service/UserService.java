package com.github.zhuyiyi1990.service;

import com.github.zhuyiyi1990.pojo.User;

public interface UserService {

    User getUserById(Long id);

    User getUserWithCondition(Long id);

    User getUserUnlessNull(Long id);

    void deleteUser(Long id);

    void clearAllUserCache();

    void updateUser(User user);

    void updateUserBefore(User user);

}