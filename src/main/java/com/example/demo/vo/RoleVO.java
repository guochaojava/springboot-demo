package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

/**
 * @author guochao
 * @since 1.0.0
 */
@Data
public class RoleVO {
    private Long id;
    private String name;
    private String code;
    private String role;

    @JsonIgnore
    private List<String> permissions;
}