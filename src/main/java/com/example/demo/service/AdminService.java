package com.example.demo.service;

import com.example.demo.dto.AdminQuery;
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

    /**
     * 根据id删除管理员
     *
     * @param id 管理员id
     * @return 删除结果
     */
    int deleteById(Long[] id);

    /**
     * 根据id更新状态
     *
     * @param id 管理员id
     * @return 更新结果
     */
    int updateStatusById(Long[] id);

    /**
     * 根据登录名更新最后一次登录时间
     *
     * @param userName  登录名
     * @param timestamp 此刻的时间戳
     */
    void updateLastLoginTimeByLoginName(String userName, Long timestamp);

    /**
     * 编辑管理员
     *
     * @param admin 数据
     * @return 编辑结果
     */
    boolean edit(Admin admin);

    /**
     * 修改个人数据
     *
     * @param admin 数据
     * @return 修改结果
     */
    int info(Admin admin);
}
