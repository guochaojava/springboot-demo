package com.example.demo.entity;

import com.example.demo.enums.CodeMessageEnum;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 响应对象（主要针对jqadmin响应数据）
 *
 * @author guochao
 * @since 1.0.0
 */
@Getter
public class ResponseEntity {
    private Integer status;
    private Object data;
    private String msg;
    private String url;
    private Integer pages;
    private Long total;

    private ResponseEntity(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseEntity(Integer status, Object data, String msg) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ResponseEntity(Integer status, Object data, String msg, Integer pages, Long total) {
        this.status = status;
        this.msg = msg;
        this.pages = pages;
        this.data = data;
        this.total = total;
    }

    public static ResponseEntity buildOk() {
        return new ResponseEntity(CodeMessageEnum.OK.code(), CodeMessageEnum.OK.msg());
    }

    public static ResponseEntity buildOk(Object data) {
        return new ResponseEntity(CodeMessageEnum.OK.code(), data, CodeMessageEnum.OK.msg());
    }

    public static ResponseEntity buildOk(Object data, Integer pages, Long total) {
        Map<String, Object> map = new HashMap<>(1);
        map.put("list", data);
        return new ResponseEntity(CodeMessageEnum.OK.code(), map, CodeMessageEnum.OK.msg(), pages, total);
    }

    public static ResponseEntity buildError() {
        return new ResponseEntity(CodeMessageEnum.ERROR.code(), CodeMessageEnum.ERROR.msg());
    }

    public static ResponseEntity buildError(Integer status, String msg) {
        return new ResponseEntity(status, msg);
    }

    public static ResponseEntity buildError(String msg) {
        return new ResponseEntity(CodeMessageEnum.ERROR.code(), msg);
    }

    public ResponseEntity reload() {
        this.url = "reload";
        return this;
    }

    public ResponseEntity url(String url) {
        this.url = url;
        return this;
    }

}