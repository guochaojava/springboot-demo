package com.example.demo.dao;

import com.example.demo.api.param.ArticleParam;
import com.example.demo.dto.ArticleQuery;
import com.example.demo.model.Article;
import com.example.demo.vo.ArticleVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ArticleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<ArticleVO> list(ArticleQuery query);

    List<Article> apiList(ArticleParam param);

    int delete(Long[] id);

    int updateStatusById(Long[] id);

    String selectContentById(Long id);
}