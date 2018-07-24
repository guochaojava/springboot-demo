package com.example.demo.config;

import com.example.demo.security.AdminUserDetailsService;
import com.example.demo.security.LoginAuthenticationFailHandler;
import com.example.demo.security.LoginAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 配置类
 *
 * @author guochao
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private AdminUserDetailsService userDetailsService;
    @Autowired
    private LoginAuthenticationSuccessHandler loginSuccessHandler;
    @Autowired
    private LoginAuthenticationFailHandler loginFailHandler;


    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //项目中用到iframe嵌入网页，然后用到springsecurity就被拦截了 浏览器报错  x-frame-options deny
        //原因是因为springSecurty使用X-Frame-Options防止网页被Frame
        http.headers().frameOptions().sameOrigin();

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/js/**", "/images/**", "/css/**", "/font/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailHandler)
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();
    }
}