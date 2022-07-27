package com.cola.sangeng_security.service;

import com.cola.sangeng_security.pojo.User;
import com.cola.sangeng_security.vo.ResponseResult;

/**
 * @Author: cola99year
 * @Date: 2022/7/27 13:10
 */
public interface LoginService {

    ResponseResult login(User user);
}
