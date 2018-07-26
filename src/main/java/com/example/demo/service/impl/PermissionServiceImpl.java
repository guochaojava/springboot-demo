package com.example.demo.service.impl;

import com.example.demo.dao.PermissionMapper;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guochao
 * @since 1.0.0
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper dao;

    @Override
    public List<Permission> listNoPages() {
        return dao.list();
    }
}