package com.example.demo.service.impl;

import com.example.demo.dao.RoleMapper;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
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
}