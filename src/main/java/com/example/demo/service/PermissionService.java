package com.example.demo.service;

import com.example.demo.dto.PermissionQuery;
import com.example.demo.model.Permission;
import com.example.demo.vo.PermissionVO;
import com.github.pagehelper.PageInfo;

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

    /**
     * 查询权限列表(分页)
     *
     * @param query 查找条件
     * @return 查询结果
     */
    PageInfo<PermissionVO> listByPage(PermissionQuery query);

    boolean edit(Permission permission);

    int delete(Long[] id);
}
