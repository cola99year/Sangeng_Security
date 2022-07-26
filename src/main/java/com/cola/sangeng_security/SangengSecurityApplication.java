package com.cola.sangeng_security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SangengSecurityApplication {

    public static void main(String[] args) {
        //run就是一个容器，可以看他里边的bean
        ConfigurableApplicationContext run = SpringApplication.run(SangengSecurityApplication.class, args);
    }

}
