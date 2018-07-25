package com.example.demo.web.controller;

import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
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

    private static final String VIEW_PREFIX = "admin/";

    @Autowired
    private RoleService roleService;


    @GetMapping("/listNoPages")
    @ResponseBody
    public Object listNoPages(){
        List<Role> list = roleService.listNoPages();
        return ResponseEntity.buildOk(list);
    }
}