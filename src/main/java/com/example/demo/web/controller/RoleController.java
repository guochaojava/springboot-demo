package com.example.demo.web.controller;

import com.example.demo.dto.RoleQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import com.example.demo.vo.RoleVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String add(){
        return VIEW_PREFIX + "add";
    }
}