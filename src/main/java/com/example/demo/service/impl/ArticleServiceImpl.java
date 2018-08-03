package com.example.demo.service.impl;

import com.example.demo.dao.ArticleMapper;
import com.example.demo.dto.ArticleQuery;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import com.example.demo.vo.ArticleVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guochao
 * @since 1.0.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper dao;

    @Override
    public PageInfo<ArticleVO> listByPage(ArticleQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        List<ArticleVO> list = dao.list(query);
        PageInfo<ArticleVO> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int add(Article article) {
        article.setCreateTime(System.currentTimeMillis());
        return dao.insertSelective(article);
    }
}