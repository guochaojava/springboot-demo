package com.example.demo.model;

public class Admin {
    /**
     * 对应数据库 admin.id
     * 
     */
    private Long id;

    /**
     * 对应数据库 admin.login_name
     * 
     */
    private String loginName;

    /**
     * 对应数据库 admin.password
     * 
     */
    private String password;

    /**
     * 对应数据库 admin.nick_name
     * 
     */
    private String nickName;

    /**
     * 对应数据库 admin.create_time
     * 
     */
    private Long createTime;

    /**
     * 对应数据库 admin.last_login_time
     * 
     */
    private Long lastLoginTime;

    /**
     * 对应数据库 admin.status
     * 状态  默认1:正常  0:封禁
     */
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    private Integer roleId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}