package com.example.demo.service.impl;

import com.example.demo.api.param.ArticleParam;
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

    @Override
    public PageInfo<Article> apiList(ArticleParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<Article> list = dao.apiList(param);
        PageInfo<Article> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Article get(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long[] id) {
        return dao.delete(id);
    }

    @Override
    public int updateStatusById(Long[] id) {
        //当前写法只适合2个状态互换 --》具体查看sql写法
        //可自行定义处理
        return dao.updateStatusById(id);
    }

    @Override
    public int edit(Article article) {
        return dao.updateByPrimaryKeySelective(article);
    }

    @Override
    public String selectContentById(Long id) {
        return dao.selectContentById(id);
    }
}