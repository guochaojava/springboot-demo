package com.example.demo.util;

import lombok.Data;

import java.util.List;

/**
 * 树结构对象
 *
 * @author guochao
 * @since 1.0.0
 */
@Data
public class TreeObject {

    private Integer pid;

    private Integer id;

    private String name;

    private String code;

    private Integer level;

    protected List<TreeObject> sub;
}