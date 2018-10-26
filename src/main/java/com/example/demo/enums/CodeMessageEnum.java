package com.example.demo.enums;

/**
 * 响应请求状态枚举类
 *
 * @author guochao
 * @since 1.0.0
 */
public enum CodeMessageEnum {

    OK(200, "操作成功"), ERROR(500, "操作失败"),;

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
