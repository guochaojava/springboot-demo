package com.example.demo.web.controller;

import com.example.demo.dto.PermissionQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.TreeObject;
import com.example.demo.util.TreeObjectUtil;
import com.example.demo.vo.PermissionVO;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author guochao
 * @since 1.0.0
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    private static final String VIEW_PREFIX = "permission/";

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/listNoPages")
    @ResponseBody
    public Object listNoPages() {
        List<Permission> list = permissionService.listNoPages();
        TreeObjectUtil mu = new TreeObjectUtil();
        List<TreeObject> treeObjects = new ArrayList<>();
        for (Permission permission : list) {
            TreeObject treeObject = new TreeObject();
            BeanUtils.copyProperties(permission, treeObject);
            treeObjects.add(treeObject);
        }
        List<TreeObject> result = mu.getChildMenuObjects(treeObjects);
        return ResponseEntity.buildOk(result);
    }

    @GetMapping()
    public String permission() {
        return VIEW_PREFIX + "/permission";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(PermissionQuery query) {
        PageInfo<PermissionVO> list = permissionService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), "查询成功", list.getPages(), list.getTotal());
    }

    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Permission permission) {
        if (permissionService.edit(permission)) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("id", permission.getId().toString());
            return ResponseEntity.buildOk(map).reload();
        } else {
            return ResponseEntity.buildError("编辑失败");
        }
    }

    @GetMapping(value = "/delete")
    @ResponseBody
    public Object delete(Integer[] id) {
        permissionService.delete(id);
        return ResponseEntity.buildOk();
    }
}