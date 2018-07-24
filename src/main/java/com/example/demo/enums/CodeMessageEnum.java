package com.example.demo.enums;

/**
 * 响应请求状态枚举类
 *
 * @author guochao
 * @since 1.0.0
 */
public enum CodeMessageEnum {

    OK(200, "OK"), ERROR(500, "ERROR"),;

    private Integer code;
    private String msg;

    CodeMessageEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
