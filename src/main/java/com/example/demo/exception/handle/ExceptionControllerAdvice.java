package com.example.demo.exception.handle;

import com.example.demo.exception.TestException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 编写全局异常处理类
 *
 * @author guochao
 * @since 1.0.0
 */
@ControllerAdvice
@Log4j2
public class ExceptionControllerAdvice {

    /**
     * 全局异常捕捉处理(返回json)
     *
     * @param ex
     * @return json对象
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> errorHandler(Exception ex) {
        //记录日志
        log.error("Exception location: {}", ex.getStackTrace());
        log.error("Exception msg: {}", ex.getMessage());
        Map<String, Object> map = new HashMap(2);
        map.put("code", 500);
        map.put("msg", ex.getMessage());
        return map;
    }

    /**
     * 拦截捕捉自定义异常 TestException.class
     *
     * @param ex
     * @return 异常页面
     */
    @ExceptionHandler(value = TestException.class)
    public ModelAndView myErrorHandler(TestException ex) {
        //记录日志
        log.error("TestException msg : {}", ex.getMsg());
        //页面跳转
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error");
        modelAndView.addObject("code", ex.getCode());
        modelAndView.addObject("msg", ex.getMsg());
        return modelAndView;
    }

}