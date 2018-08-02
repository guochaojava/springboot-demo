package com.example.demo.vo;

import lombok.Data;

/**
 * @author guochao
 * @since 1.0.0
 */
@Data
public class ArticleVO {

    private Long id;

    private String title;

    private String cover;

    private String intro;

    private String img;

    private String source;

    private Integer sort;

    private Long createTime;

    private Boolean status;

    private String content;
}