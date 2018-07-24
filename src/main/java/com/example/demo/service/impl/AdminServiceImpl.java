package com.example.demo.service.impl;

import com.example.demo.dao.AdminMapper;
import com.example.demo.dto.query.AdminQuery;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.vo.AdminVO;
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
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper dao;

    @Override
    public Admin selectByLoginName(String loginName) {
        return dao.selectByLoginName(loginName);
    }

    @Override
    public PageInfo<AdminVO> listByPage(AdminQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<AdminVO> list = dao.list(query);
        PageInfo<AdminVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}