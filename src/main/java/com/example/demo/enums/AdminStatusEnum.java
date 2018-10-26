package com.example.demo.enums;

/**
 * 后台用户状态枚举类
 *
 * @author guochao
 * @since 1.0.0
 */
public enum AdminStatusEnum {

    NORMAL(1, "正常"), FORBIDDEN(0, "禁用"),
    ;

    private Integer code;
    private String msg;

    AdminStatusEnum(Integer code, String msg) {
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
