package com.example.demo.service;

import com.example.demo.dto.ArticleQuery;
import com.example.demo.model.Article;
import com.example.demo.vo.ArticleVO;
import com.github.pagehelper.PageInfo;

public interface ArticleService {
    PageInfo<ArticleVO> listByPage(ArticleQuery query);

    int add(Article article);
}
