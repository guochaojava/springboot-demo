package com.example.demo.web.controller;

import com.example.demo.model.Admin;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author guochao
 * @since 1.0.0
 */
@Controller
public class IndexController {

    private static final String VIEW_PREFIX = "index/";

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String index(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Admin admin = adminService.selectByLoginName(username);

        model.addAttribute("user", admin);

        return VIEW_PREFIX + "index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return VIEW_PREFIX + "welcome";
    }

    @GetMapping("/info")
    public String info() {
        return VIEW_PREFIX + "info";
    }
}