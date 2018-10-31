package com.example.demo.web.controller;

import com.example.demo.dto.AdminQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Admin;
import com.example.demo.model.Role;
import com.example.demo.service.AdminService;
import com.example.demo.service.RoleService;
import com.example.demo.vo.AdminVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public String admin(Model model) {
        List<Role> list = roleService.listNoPages();
        model.addAttribute("roleList", list);
        return VIEW_PREFIX + "list";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(AdminQuery query) {
        PageInfo<AdminVO> list = adminService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), "查询成功", list.getPages(), list.getTotal());
    }

    @GetMapping("/add")
    public String toAdd(Model model) {
        List<Role> list = roleService.listNoPages();
        model.addAttribute("roleList", list);
        return VIEW_PREFIX + "add";
    }


    @PostMapping("/add")
    @ResponseBody
    public Object add(Admin admin) {
        if (adminService.add(admin)) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("id", admin.getId().toString());
            return ResponseEntity.buildOk(map).reload();
        }
        return ResponseEntity.buildError("添加失败");
    }

    @GetMapping("edit")
    public String toEdit(Model model) {
        List<Role> list = roleService.listNoPages();
        model.addAttribute("roleList", list);
        return VIEW_PREFIX + "edit";
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

    @GetMapping(value = "/delete")
    @ResponseBody
    public Object delete(Long[] id) {
        adminService.delete(id);
        return ResponseEntity.buildOk();
    }
}