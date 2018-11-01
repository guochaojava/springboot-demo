package com.example.demo.service;

import com.example.demo.api.param.ArticleParam;
import com.example.demo.dto.ArticleQuery;
import com.example.demo.model.Article;
import com.example.demo.vo.ArticleVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ArticleService {
    PageInfo<ArticleVO> listByPage(ArticleQuery query);

    int add(Article article);

    PageInfo<Article> apiList(ArticleParam param);

    Article get(Long id);
}
