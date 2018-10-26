package com.example.demo.web.controller;

import com.example.demo.dto.AdminQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.vo.AdminVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

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
    public String admin() {
        return VIEW_PREFIX + "admin";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(AdminQuery query) {
        PageInfo<AdminVO> list = adminService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), "查询成功", list.getPages(), list.getTotal());
    }

    @PostMapping("/edit")
    @ResponseBody
    public Object edit(Admin admin) {
        if (adminService.edit(admin)) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("id", admin.getId().toString());
            return ResponseEntity.buildOk(map).reload();
        } else {
            return ResponseEntity.buildError("编辑失败");
        }
    }

    @PostMapping("/info")
    @ResponseBody
    public Object info(Admin admin) {
        adminService.info(admin);
        return ResponseEntity.buildOk("修改成功");
    }

    @GetMapping("/delete")
    @ResponseBody
    public Object delete(Long[] id) {
        int result = adminService.deleteById(id);
        if (result > 0) {
            return ResponseEntity.buildOk().reload();
        } else {
            return ResponseEntity.buildError("删除失败");
        }
    }

    @GetMapping("/status")
    @ResponseBody
    public Object status(Long[] id) {
        int result = adminService.updateStatusById(id);
        if (result > 0) {
            return ResponseEntity.buildOk().reload();
        } else {
            return ResponseEntity.buildError("更新失败");
        }
    }
}