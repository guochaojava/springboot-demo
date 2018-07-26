package com.example.demo.dao;

import com.example.demo.dto.RoleQuery;
import com.example.demo.model.Role;
import com.example.demo.vo.RoleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectByAdminId(Long id);

    List<Role> list();

    List<RoleVO> list(RoleQuery query);
}