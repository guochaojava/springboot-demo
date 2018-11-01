package com.example.demo.api.param;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 */
@Setter
@Getter
public class ArticleParam extends BaseListParam {
    @ApiParam(value = "文章标题,填写表示模糊查询")
    private String title;
}
