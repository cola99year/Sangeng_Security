package com.cola.sangeng_security.filter;

import com.cola.sangeng_security.pojo.LoginUser;
import com.cola.sangeng_security.utils.JwtUtil;
import com.cola.sangeng_security.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取前端请求头的token
        String token = request.getHeader("token");
        //没有token要放行，不然登录页面都进不了
        if (!StringUtils.hasText(token)) {
            //放行
            filterChain.doFilter(request, response);
            return;//直接return，不走下面解析token的代码了
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = (LoginUser) redisUtil.get(redisKey);
        //如果注销了，Redis数据就被删除，此时抛出异常用户未登录！
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录");
        }
        //存入SecurityContextHolder，
        // TODO 每次请求都存一次？？？？
        //TODO 获取权限信息封装到Authentication中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser,null,null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //SecurityContextHolder.getContext().getAuthentication()
        //放行
        filterChain.doFilter(request, response);
    }
}
