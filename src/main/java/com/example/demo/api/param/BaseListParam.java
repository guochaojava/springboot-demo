package com.example.demo.api.param;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 */
@Setter
@Getter
public class BaseListParam {
    @ApiParam(value = "请求页码,默认值1",example = "1")
    @Builder.Default
    private Integer pageNum = 1;

    @ApiParam(value = "每页大小,默认值10",example = "10")
    @Builder.Default
    private Integer pageSize = 10;
}
