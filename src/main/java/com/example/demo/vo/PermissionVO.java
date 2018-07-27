package com.example.demo.vo;

import lombok.Data;

/**
 * @author guochao
 * @since 1.0.0
 */
@Data
public class PermissionVO {
    private Integer id;
    private Integer pid;
    private String name;
    private Integer level;
    private Integer sort;
    private String code;
}