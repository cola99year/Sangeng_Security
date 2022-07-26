package com.cola.sangeng_security;

import com.cola.sangeng_security.mapper.UserMapper;
import com.cola.sangeng_security.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class SangengSecurityApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }
    @Test
    public void TestBCryptPasswordEncoder(){
        //查看括号内的明文加密后是:$2a$10$wJHiIzNfkyq.rb/FrTbrZe2PfGtc075z2sWRLoDKzJ.3Vu9mG0tO2
        System.out.println(new BCryptPasswordEncoder().encode("123"));
        //对比明文和密文是否匹配，若匹配则为True
        System.out.println(new BCryptPasswordEncoder().matches("123","$2a$10$wJHiIzNfkyq.rb/FrTbrZe2PfGtc075z2sWRLoDKzJ.3Vu9mG0tO2"));
    }

}
