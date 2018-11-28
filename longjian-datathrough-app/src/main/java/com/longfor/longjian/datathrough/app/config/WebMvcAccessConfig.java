package com.longfor.longjian.datathrough.app.config;

import com.longfor.gaia.gfs.web.core.config.TraceInterceptor;
import com.longfor.longjian.datathrough.app.security.interceptor.GlobalAccessInterceptor;
import com.longfor.longjian.datathrough.app.vo.WhiteVo;
import org.springframework.beans.factory.annotation.Value;
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
public class WebMvcAccessConfig extends WebMvcConfigurerAdapter {

    @Value("${master.whitelist.enabled}")
    private boolean enabled;

    @Value("${master.whitelist.list}")
    private String whitelist;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        WhiteVo whiteVo=new WhiteVo();
        whiteVo.setEnabled(enabled);
        whiteVo.setWhitelist(whitelist);

        registry.addInterceptor(new TraceInterceptor())
                .addPathPatterns("/**");
        registry.addInterceptor(new GlobalAccessInterceptor(whiteVo))
                .addPathPatterns("/master/data/**");
        super.addInterceptors(registry);
    }


}