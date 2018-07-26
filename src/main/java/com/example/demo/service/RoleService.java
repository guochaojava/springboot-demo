package com.example.demo.service;

import com.example.demo.dto.RoleQuery;
import com.example.demo.model.Role;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.vote.RoleVoter;

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
}
