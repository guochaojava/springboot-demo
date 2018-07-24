package com.example.demo.service;

import com.example.demo.dto.query.AdminQuery;
import com.example.demo.model.Admin;
import com.example.demo.vo.AdminVO;
import com.github.pagehelper.PageInfo;

/**
 * 管理员service类
 *
 * @author guochao
 */
public interface AdminService {
    /**
     * 根据登录名查询实体
     *
     * @param loginName 登录账号
     * @return 查询结果
     */
    Admin selectByLoginName(String loginName);

    /**
     * 获取管理员列表（分页）
     *
     * @param query
     * @return
     */
    PageInfo<AdminVO> listByPage(AdminQuery query);
}
