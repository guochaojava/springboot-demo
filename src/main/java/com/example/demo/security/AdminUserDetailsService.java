package com.example.demo.security;

import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.service.AdminService;
import com.example.demo.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 自定义管理员用户登录认证
 *
 * @author guochao
 * @since 1.0.0
 */
@Component
@Log4j2
public class AdminUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;


    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        log.info("管理员的用户名: {}", loginName);
        Admin adminUser = adminService.selectByLoginName(loginName);

        //查无此用户
        if (Objects.isNull(adminUser)) {
            log.error("查无此用户: {}", loginName);
            throw new UsernameNotFoundException(loginName + " not found");
        }

        //查询用户角色信息
        List<Role> roleList = roleService.selectByAdminId(adminUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        return User.withUsername(loginName).password(adminUser.getPassword()).authorities(authorities).build();
    }
}