package com.example.demo.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 * @since 1.0.0
 **/
@Getter
@Setter
public class JwtProperties {
    private String key;
    private String exp;
}
