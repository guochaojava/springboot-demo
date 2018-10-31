package com.example.demo.service.impl;

import com.example.demo.dao.PermissionMapper;
import com.example.demo.dto.PermissionQuery;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.vo.PermissionVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        return dao.listNoPages();
    }

    @Override
    public PageInfo<PermissionVO> listByPage(PermissionQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<PermissionVO> list = dao.list(query);
        PageInfo<PermissionVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean edit(Permission permission) {
        //更新操作
        if (Objects.nonNull(permission.getId())) {
           return dao.update(permission);
        }
        //添加操作
        else {
            return dao.add(permission);

        }
    }

    @Override
    public int delete(Long[] id) {
        //删除权限
        dao.delete(id);
        //删除已选权限的角色
        return dao.deletePermissionIdInUsedRole(id);
    }
}