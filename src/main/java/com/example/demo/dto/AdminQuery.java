package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 * @since 1.0.0
 */
@Setter
@Getter
public class AdminQuery extends BaseQuery {
    private String loginName;
    private String nickName;
}