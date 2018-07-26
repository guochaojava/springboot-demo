package com.example.demo.service;

import com.example.demo.model.Permission;

import java.util.List;

/**
 * 权限service类
 *
 * @author guochao
 * @since 1.0.0
 */
public interface PermissionService {

    /**
     * 查询权限列表（不分页）
     *
     * @return 权限列表
     */
    List<Permission> listNoPages();
}
