package com.example.demo.config;

import com.example.demo.property.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 自定义属性配置类
 *
 * @author guochao
 * @since 1.0.0
 **/
@Configuration
@ConfigurationProperties(prefix = "example")
@Setter
@Getter
public class PropertiesConfig {
    private CookieProperties cookie = new CookieProperties();
    private JwtProperties jwt = new JwtProperties();
    private RedisProperties redis = new RedisProperties();
    private UploadProperties upload = new UploadProperties();
    private WeChatProperties wechat = new WeChatProperties();
    private DruidProperties druid = new DruidProperties();
}
