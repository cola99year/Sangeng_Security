package com.cola.sangeng_security.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {//实现UserDetails接口，下面都是重写接口的方法

    private User user;

    //存储权限信息
    private List<String> permissions;


    public LoginUser(User user,List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }


    //存储SpringSecurity所需要的权限信息的集合
    @JSONField(serialize = false)
    private List<GrantedAuthority> authorities;

    @Override
    public  Collection<? extends GrantedAuthority> getAuthorities() {
        if(authorities!=null){
            return authorities;
        }
        //把permissions中字符串类型的权限信息转换成GrantedAuthority对象存入authorities中
        authorities = permissions.stream().
                map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return authorities;

            //if(authorities!=null){
            //    return authorities;
            //}
            //for (String permission:permissions) {
            //    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            //    authorities.add(authority);
            //}
            //return authorities;
    }

    //获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //获取用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //账户是否没过期？
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否还没锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //是否还没超时？
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否可用？
    @Override
    public boolean isEnabled() {
        return true;
    }
}
