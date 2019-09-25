package com.zhy.submit.teacher.config;

import com.zhy.submit.teacher.config.intercepors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Value("${file.staticAccessPath}")
    private String staticAccessPath;
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    /*@Value("${file.previewPDF}")
    private String previewPDF;*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder);
        /*registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+previewPDF);*/
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/teacher/login");
    }
}
