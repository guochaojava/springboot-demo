package com.example.demo.service.impl;

import com.example.demo.dao.RoleMapper;
import com.example.demo.dto.RoleQuery;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import com.example.demo.vo.AdminVO;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guochao
 * @since 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper dao;

    @Override
    public List<Role> selectByAdminId(Long id) {
        return dao.selectByAdminId(id);
    }

    @Override
    public List<Role> listNoPages() {
        return dao.list();
    }

    @Override
    public PageInfo<RoleVO> listByPage(RoleQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<RoleVO> list = dao.list(query);
        PageInfo<RoleVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}