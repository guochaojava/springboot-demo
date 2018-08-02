package com.example.demo.model;

public class Article {
    /**
     * 对应数据库 article.id
     * 
     */
    private Long id;

    /**
     * 对应数据库 article.title
     * 标题
     */
    private String title;

    /**
     * 对应数据库 article.cover
     * 封面图片
     */
    private String cover;

    /**
     * 对应数据库 article.intro
     * 简介
     */
    private String intro;

    /**
     * 对应数据库 article.img
     * 内容图
     */
    private String img;

    /**
     * 对应数据库 article.source
     * 来源
     */
    private String source;

    /**
     * 对应数据库 article.sort
     * 排序
     */
    private Integer sort;

    /**
     * 对应数据库 article.create_time
     * 创建日期
     */
    private Long createTime;

    /**
     * 对应数据库 article.status
     * 默认 2：待审核  1：正常  3：驳回
     */
    private Boolean status;

    /**
     * 对应数据库 article.content
     * 内容
     */
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}