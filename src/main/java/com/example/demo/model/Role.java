package com.example.demo.model;

public class Role {
    /**
     * 对应数据库 role.id
     * 
     */
    private Long id;

    /**
     * 对应数据库 role.name
     * 
     */
    private String name;

    /**
     * 对应数据库 role.code
     * 
     */
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}