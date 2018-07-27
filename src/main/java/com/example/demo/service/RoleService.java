package com.example.demo.service;

import com.example.demo.dto.RoleQuery;
import com.example.demo.model.Role;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageInfo;

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

    /**
     * 查询角色列表（不分页）
     *
     * @return
     */
    List<Role> listNoPages();

    /**
     * 查询角色列表（分页）
     *
     * @param query 查询条件
     * @return 查询结果
     */
    PageInfo<RoleVO> listByPage(RoleQuery query);

    /**
     * 添加角色
     *
     * @param role        角色信息
     * @param permissions 权限列表
     * @return 添加结果
     */
    boolean add(Role role, List<Integer> permissions);

    /**
     * 更新角色信息
     *
     * @param role 角色信息
     * @return 更新结果
     */
    int update(Role role);

    /**
     * 更新
     *
     * @param id
     * @param permissions
     * @return
     */
    int updateRolePermission(Long id, List<Integer> permissions);
}
