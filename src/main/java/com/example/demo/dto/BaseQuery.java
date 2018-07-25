package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 * @since 1.0.0
 */
@Setter
@Getter
public class BaseQuery {

    @Builder.Default
    private Integer pageNum =0;
    @Builder.Default
    private Integer pageSize = 99999;
}