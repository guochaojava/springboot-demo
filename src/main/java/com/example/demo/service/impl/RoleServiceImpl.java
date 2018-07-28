package com.example.demo.service.impl;

import com.example.demo.dao.RoleMapper;
import com.example.demo.dto.RoleQuery;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.sun.deploy.util.StringUtils.join;


/**
 * @author guochao
 * @since 1.0.0
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper dao;

    @Override
    public List<Role> selectByAdminId(Long id) {
        return dao.selectByAdminId(id);
    }

    @Override
    public List<Role> listNoPages() {
        return dao.listNoPages();
    }

    @Override
    public PageInfo<RoleVO> listByPage(RoleQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<RoleVO> list = dao.list(query);
        int length = list.size();
        for (int i = 0; i < length; i++) {
            RoleVO vo = list.get(i);
            if (Objects.nonNull(vo.getPermissions())) {
                vo.setRole(join(vo.getPermissions(), ","));
            }
            list.set(i, vo);
        }
        PageInfo<RoleVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean add(Role role, List<Integer> permissions) {
        if (dao.add(role)) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleId", role.getId());
            map.put("permissions", permissions);
            dao.addPermissions(map);
        }
        return true;
    }

    @Override
    public int update(Role role) {
        return dao.updateByPrimaryKeySelective(role);
    }

    @Override
    public int updateRolePermission(Long id, List<Integer> permissions) {
        //删除
        dao.deletebyRoleId(id);
        //更新
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", id);
        map.put("permissions", permissions);
        return dao.updateRolePermission(map);
    }
}