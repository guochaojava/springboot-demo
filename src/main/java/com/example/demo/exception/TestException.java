package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author guochao
 * @since 1.0.0
 */
@Setter
@Getter
public class TestException extends RuntimeException {

    public TestException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

}