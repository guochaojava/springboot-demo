package com.example.demo.dao;

import com.example.demo.dto.query.AdminQuery;
import com.example.demo.model.Admin;
import com.example.demo.vo.AdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Admin selectByLoginName(String loginName);

    List<AdminVO> list(AdminQuery query);
}