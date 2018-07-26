package com.example.demo.model;

public class Permission {
    /**
     * 对应数据库 permission.id
     * 
     */
    private Integer id;

    /**
     * 对应数据库 permission.pid
     * 父id
     */
    private Integer pid;

    /**
     * 对应数据库 permission.name
     * 权限描述
     */
    private String name;

    /**
     * 对应数据库 permission.level
     * 级别
     */
    private Integer level;

    /**
     * 对应数据库 permission.sort
     * 排序字段
     */
    private Integer sort;

    /**
     * 对应数据库 permission.code
     * 权限编码
     */
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}