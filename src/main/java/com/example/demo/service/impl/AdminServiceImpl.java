package com.example.demo.service.impl;

import com.example.demo.dao.AdminMapper;
import com.example.demo.dto.AdminQuery;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.vo.AdminVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author guochao
 * @since 1.0.0
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper dao;
    @Autowired
    private PasswordEncoder passwordEncoder;


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

    @Override
    public int deleteById(Long[] id) {
        return dao.deleteById(id);
    }

    @Override
    public int updateStatusById(Long[] id) {
        return dao.updateStatusById(id);
    }

    @Override
    public void updateLastLoginTimeByLoginName(String userName, Long timestamp) {
        dao.updateLastLoginTimeByLoginName(userName, timestamp);
    }

    @Override
    public boolean edit(Admin admin) {
        //更新操作
        if (Objects.nonNull(admin.getId())) {
            if (Objects.nonNull(admin.getPassword())) {
                String encodePassword = passwordEncoder.encode(admin.getPassword());
                admin.setPassword(encodePassword);
            }
            dao.update(admin);
            dao.updateRole(admin);
        }
        //添加操作
        else {
            //spring security 版本在5.0后就要加个PasswordEncoder了
            String encodePassword = passwordEncoder.encode(admin.getPassword());
            admin.setPassword(encodePassword);

            Long timestamp = System.currentTimeMillis();
            admin.setCreateTime(timestamp);
            admin.setLastLoginTime(timestamp);
            if (dao.add(admin)) {
                dao.addRole(admin);
            }
        }
        return true;
    }
}