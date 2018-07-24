package com.example.demo.security;

import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.service.AdminService;
import com.example.demo.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AdminUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserDetailsService.class);

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        LOGGER.info("管理员的用户名: {}", loginName);
        Admin adminUser = adminService.selectByLoginName(loginName);

        //查无此用户
        if (Objects.isNull(adminUser)) {
            LOGGER.error("查无此用户: {}", loginName);
            throw new UsernameNotFoundException(loginName + " not found");
        }
        //spring security 版本在5.0后就要加个PasswordEncoder了
        String encodePassword = passwordEncoder.encode(adminUser.getPassword());
        adminUser.setPassword(encodePassword);

        //查询用户角色信息
        List<Role> roleList = roleService.selectByAdminId(adminUser.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList) {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return User.withUsername(loginName).password(adminUser.getPassword()).authorities(authorities).build();
    }
}