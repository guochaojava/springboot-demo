package com.example.demo.web.controller;

import com.example.demo.dto.query.AdminQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.service.AdminService;
import com.example.demo.vo.AdminVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 管理员控制器
 *
 * @author guochao
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {


    private static final String VIEW_PREFIX = "admin/";

    @Autowired
    private AdminService adminService;

    @GetMapping()
    private String admin() {
        return VIEW_PREFIX + "admin";
    }

    @GetMapping("/list")
    @ResponseBody
    private Object list(AdminQuery query) {
        PageInfo<AdminVO> list = adminService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), list.getPages(), list.getTotal());
    }
}