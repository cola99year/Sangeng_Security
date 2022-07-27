package com.cola.sangeng_security.service.impl;

import com.cola.sangeng_security.pojo.LoginUser;
import com.cola.sangeng_security.pojo.User;
import com.cola.sangeng_security.service.LoginService;
import com.cola.sangeng_security.utils.JwtUtil;
import com.cola.sangeng_security.utils.RedisCache;
import com.cola.sangeng_security.utils.RedisUtil;
import com.cola.sangeng_security.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseResult login(User user) {
        //Authentication是接口的实现类，封装用户密码返回Authentication类型的参数
        UsernamePasswordAuthenticationToken authenticationToken = new  						                        UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //authenticationManager接口调用authenticate方法，方法返回的是Authentication对象，需要Authentication类型的参数。
        //Authentication是接口，使用这个接口的实现类来把用户的账号密码封装成这个类型的参数。供authenticationManager接口的authenticate方法调用
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        
        //如果查询不到用户，就抛出异常
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //把authenticate对象强转为LoginUser对象
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //使用userid生成token
        String jwt = JwtUtil.createJWT(userId);
        System.out.println(jwt);
        //authenticate存入redis，如:  "login:1"为key，LoginUser对象为value
        redisUtil.set("login:"+userId,loginUser);
        //把token响应给前端
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisUtil.del("login:"+userid);
        return new ResponseResult(200,"退出成功");
    }
}