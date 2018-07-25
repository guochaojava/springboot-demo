package com.example.demo.vo;

import lombok.Data;

/**
 * @author guochao
 * @since 1.0.0
 */
@Data
public class AdminVO {
    private Long id;
    private String loginName;
    private String nickName;
    private String roleName;
    private Long roleId;
    private Long createTime;
    private Long lastLoginTime;
    private Boolean status;
}