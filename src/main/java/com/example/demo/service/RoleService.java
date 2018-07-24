package com.example.demo.service;

import com.example.demo.model.Role;

import java.util.List;

/**
 * 角色service类
 *
 * @author guochao
 */
public interface RoleService {
    /**
     * 根据管理员id查询所属角色列表
     *
     * @param id
     * @return
     */
    List<Role> selectByAdminId(Long id);
}
