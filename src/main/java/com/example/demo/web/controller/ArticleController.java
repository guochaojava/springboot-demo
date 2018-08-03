package com.example.demo.web.controller;

import com.example.demo.dto.ArticleQuery;
import com.example.demo.entity.ResponseEntity;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import com.example.demo.vo.ArticleVO;
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
 * @author guochao
 * @since 1.0.0
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    private static final String VIEW_PREFIX = "article/";

    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public String article() {
        return VIEW_PREFIX + "list";
    }

    @GetMapping("/list")
    @ResponseBody
    public Object list(ArticleQuery query) {
        PageInfo<ArticleVO> list = articleService.listByPage(query);
        return ResponseEntity.buildOk(list.getList(), "查询成功", list.getPages(), list.getTotal());
    }


    @GetMapping("/add")
    public String toAdd() {
        return VIEW_PREFIX + "add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Object add(Article article) {
        articleService.add(article);
        Map<String, Object> map = new HashMap<>(1);
        map.put("id", article.getId().toString());
        return ResponseEntity.buildOk(map).url("/article");
    }
}