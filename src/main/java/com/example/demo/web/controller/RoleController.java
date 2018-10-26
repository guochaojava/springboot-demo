package com.example.demo.web.controller;

import cn.hutool.core.lang.Console;
import com.example.demo.dto.RoleQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 角色控制器
 *
 * @author guochao
 * @since 1.0.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final String VIEW_PREFIX = "role/";

    @Autowired
    private RoleService roleService;


    @GetMapping("/listNoPages")
    @ResponseBody
    public Object listNoPages() {
        List<Role> list = roleService.listNoPages();
        return ResponseEntity.buildOk(list);
    }

    @GetMapping
    public String role() {
        return VIEW_PREFIX + "role";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(RoleQuery query) {
        PageInfo<RoleVO> list = roleService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), "查询成功", list.getPages(), list.getTotal());
    }

    @GetMapping("/add")
    public String toAdd() {
        return VIEW_PREFIX + "add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(Role role, @RequestParam(value = "roles[]", required = false) List<Integer> roles, HttpServletRequest request) {
        if (Objects.isNull(roles)) {
            return ResponseEntity.buildError("请选择所属权限");
        }
        roleService.add(role, roles);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", role.getId().toString());
        return ResponseEntity.buildOk(map).url(request.getContextPath() + "/role");
    }

    @GetMapping("/update")
    public String toEdit() {
        return VIEW_PREFIX + "edit";
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public Object update(Role role, @RequestParam(value = "roles[]", required = false) List<Integer> permissions,HttpServletRequest request) {
        roleService.update(role);
        if (Objects.nonNull(permissions)) {
            roleService.updateRolePermission(role.getId(), permissions);
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", role.getId().toString());
        return ResponseEntity.buildOk(map).url(request.getContextPath() +"/role");
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer[] id) {
        roleService.delete(id);
        return ResponseEntity.buildOk();
    }
}