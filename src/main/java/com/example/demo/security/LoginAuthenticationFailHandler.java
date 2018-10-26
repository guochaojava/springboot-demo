package com.example.demo.security;

import cn.hutool.json.JSONUtil;
import com.example.demo.entity.ResponseEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义登录失败处理类
 *
 * @author guochao
 * @since 1.0.0
 */
@Component
@Log4j2
public class LoginAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        log.error("登录失败");

        String msg = "登录失败";
        //处理登录失败信息
        if ("User account is locked".equals(e.getMessage())) {
            msg = "登录账号已被禁用";
        }
        if("Bad credentials".equals(e.getMessage())){
            msg = "登录账号或密码错误";
        }

        if("User account has expired".equals(e.getMessage())){
            msg = "登录账号已过期";
        }

        //将 登录失败 信息打包成json格式返回
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSONUtil.toJsonStr(ResponseEntity.buildError(msg)));
    }
}