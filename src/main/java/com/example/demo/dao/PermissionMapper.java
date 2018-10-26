package com.example.demo.dao;

import com.example.demo.dto.PermissionQuery;
import com.example.demo.model.Permission;
import com.example.demo.vo.PermissionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> listNoPages();

    List<PermissionVO> list(PermissionQuery query);

    boolean add(Permission permission);

    boolean update(Permission permission);

    int delete(Integer[] id);

    int deletePermissionIdInUsedRole(Integer[] id);
}