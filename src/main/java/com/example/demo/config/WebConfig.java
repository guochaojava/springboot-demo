package com.example.demo.config;

import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;


/**
 * web配置类
 *
 * @author guochao
 * @since 1.0.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Autowired
    private FreeMarkerConfigurer configurer;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver("", ".html");
        freeMarkerViewResolver.setViewClass(MyFreeMarkerView.class);
        freeMarkerViewResolver.setCache(true);
        freeMarkerViewResolver.setContentType("text/html;charset=UTF-8");
        return freeMarkerViewResolver;
    }


    @PostConstruct
    public void freeMarkerConfigurer() {
        List<String> tlds = new ArrayList<String>();
        tlds.add("/static/tags/security.tld");
        TaglibFactory taglibFactory = configurer.getTaglibFactory();
        taglibFactory.setClasspathTlds(tlds);
        if (taglibFactory.getObjectWrapper() == null) {
            taglibFactory.setObjectWrapper(configurer.getConfiguration().getObjectWrapper());
        }
    }
}
