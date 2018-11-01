package com.example.demo.api;

import com.example.demo.api.param.ArticleParam;
import com.example.demo.entity.ApiResponseEntity;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章相关接口
 *
 * @author guochao
 */
@RestController
@Api(tags = "文章相关接口", description = "仅供提供给微信小程序【测试描述效果】")
@RequestMapping("/api")
public class ArticleApi {

    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "获取文章列表", notes = "传统分页接口")
    @GetMapping("/article")
    public Object list(ArticleParam param) {
        PageInfo<Article> list = articleService.apiList(param);
        return ApiResponseEntity.buildOk(list.getList(), list.getPages(), list.getTotal());
    }

    @ApiOperation(value = "获取文章列表", notes = "restful风格分页接口")
    @GetMapping("/article/{pageNum}/{pageSize}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "请求页码", dataType = "int", required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "int", required = true)
    })
    public Object resultFullList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        ArticleParam param = new ArticleParam();
        param.setPageNum(pageNum);
        param.setPageSize(pageSize);
        PageInfo<Article> list = articleService.apiList(param);
        return ApiResponseEntity.buildOk(list.getList(), list.getPages(), list.getTotal());
    }

    @ApiOperation(value = "获取文章", notes = "restful风格")
    @GetMapping("/article/{id}")
    @ApiImplicitParam(name = "id", required = true, value = "文章id", dataType = "int")
    public Object get(@PathVariable Long id) {
        Article article = articleService.get(id);
        return ApiResponseEntity.buildOk(article);
    }
}
