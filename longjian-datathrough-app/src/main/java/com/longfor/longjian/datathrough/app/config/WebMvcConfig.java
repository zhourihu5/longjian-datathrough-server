package com.longfor.longjian.datathrough.app.config;

import com.longfor.gaia.gfs.web.core.config.TraceInterceptor;
import com.longfor.longjian.datathrough.app.security.interceptor.GlobalAccessInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * auth shanhonghao
 * date 2018-11-26
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new GlobalAccessInterceptor())
                .addPathPatterns("/master/data/**");
        super.addInterceptors(registry);
    }


}