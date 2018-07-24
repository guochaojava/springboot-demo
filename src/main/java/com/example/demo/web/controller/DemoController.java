package com.example.demo.web.controller;

import com.example.demo.exception.TestException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 演示controller
 *
 * @author guochao
 * @since 1.0.0
 */
@Controller
public class DemoController {

    @GetMapping("/exception")
    public String exception() {

        throw new TestException(202, "错误演示");
    }
}