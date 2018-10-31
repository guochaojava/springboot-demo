package com.example.demo.dao;

import com.example.demo.dto.AdminQuery;
import com.example.demo.model.Admin;
import com.example.demo.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminMapper {
    Admin selectByPrimaryKey(Long id);

    Admin selectByLoginName(String loginName);

    List<AdminVO> list(AdminQuery query);

    int deleteById(Long[] id);

    int updateStatusById(Long[] id);

    void updateLastLoginTimeByLoginName(@Param("loginName") String userName, @Param("lastLoginTime") Long timestamp);

    boolean add(Admin admin);

    void addRole(Admin admin);

    void update(Admin admin);

    void updateRole(Admin admin);

    int info(Admin admin);

    int delete(Long[] id);
}