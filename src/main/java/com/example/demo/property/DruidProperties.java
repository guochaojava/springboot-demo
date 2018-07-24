package com.example.demo.property;

import lombok.Getter;
import lombok.Setter;

/**
 * @author guochao
 * @since 1.0.0
 **/
@Setter
@Getter
public class DruidProperties {
    private String username;
    private String password;
    private String url;
    private String driverClassName;
    private String filters;
    private int maxActive;
    private int initialSize;
    private long timeBetweenEvictionRunsMillis;
    private long minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private int maxOpenPreparedStatements;
    private boolean removeAbandoned;
    private int removeAbandonedTimeout;
    private boolean logAbandoned;
}
