package com.cola.sangeng_security.controller;

import com.cola.sangeng_security.service.LoginService;
import com.cola.sangeng_security.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cola99year
 * @Date: 2022/7/27 14:38
 */
@RestController
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
