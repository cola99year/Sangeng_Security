package com.cola.sangeng_security.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cola99year
 * @Date: 2022/7/26 15:16
 */
@RestController
public class helloController {

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('admin')")
    public String hello(){
        return "hello";
    }
}
