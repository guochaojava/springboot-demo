package com.example.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class User {
    /**
     * 对应数据库 user.id
     * 
     */
    private Long id;

    /**
     * 对应数据库 user.nick_name
     * 昵称
     */
    private String nickName;

    /**
     * 对应数据库 user.password
     * 密码
     */
    private String password;

    /**
     * 对应数据库 user.phone
     * 手机号
     */
    private String phone;

    /**
     * 对应数据库 user.create_time
     * 创建时间
     */
    private Long createTime;

    /**
     * 对应数据库 user.update_time
     * 更新时间
     */
    private Long updateTime;

    /**
     * 对应数据库 user.last_login_time
     * 最后一次登录时间
     */
    private Long lastLoginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}