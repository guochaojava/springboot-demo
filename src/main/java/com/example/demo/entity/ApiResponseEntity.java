package com.example.demo.entity;

import com.example.demo.enums.CodeMessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

/**
 * 接口响应对象
 *
 * @author guochao
 * @since 1.0.0
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseEntity {

    private Integer status;
    private Object data;
    private String msg;
    private Integer pages;
    private Long total;

    private ApiResponseEntity(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ApiResponseEntity(Integer status, Object data, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ApiResponseEntity(Integer status, Object data, String msg, Integer pages, Long total) {
        this.status = status;
        this.msg = msg;
        this.pages = pages;
        this.data = data;
        this.total = total;
    }

    public static ApiResponseEntity buildOk() {
        return new ApiResponseEntity(CodeMessageEnum.OK.code(), CodeMessageEnum.OK.msg());
    }

    public static ApiResponseEntity buildOk(Object data) {
        return new ApiResponseEntity(CodeMessageEnum.OK.code(), data, CodeMessageEnum.OK.msg());
    }

    public static ApiResponseEntity buildOk(Object data, String msg) {
        return new ApiResponseEntity(CodeMessageEnum.OK.code(), data, msg);
    }


    public static ApiResponseEntity buildOk(Object data, Integer pages, Long total) {
        return new ApiResponseEntity(CodeMessageEnum.OK.code(), data, CodeMessageEnum.OK.msg(), pages, total);
    }

    public static ApiResponseEntity buildOk(Object data, String msg, Integer pages, Long total) {
        return new ApiResponseEntity(CodeMessageEnum.OK.code(), data, msg, pages, total);
    }

    public static ApiResponseEntity buildError() {
        return new ApiResponseEntity(CodeMessageEnum.ERROR.code(), CodeMessageEnum.ERROR.msg());
    }

    public static ApiResponseEntity buildError(Integer status, String msg) {
        return new ApiResponseEntity(status, msg);
    }

    public static ApiResponseEntity buildError(String msg) {
        return new ApiResponseEntity(CodeMessageEnum.ERROR.code(), msg);
    }
}