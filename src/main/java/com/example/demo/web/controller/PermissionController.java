package com.example.demo.web.controller;

import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Permission;
import com.example.demo.service.PermissionService;
import com.example.demo.util.TreeObject;
import com.example.demo.util.TreeObjectUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}