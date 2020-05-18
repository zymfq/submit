package com.zym.submit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通过url找到文件
 * @author zym
 * @date 2019-09-16-13:29
 */
@Configuration
public class UploadFilePathConfig implements WebMvcConfigurer {

    @Value("${file.staticAccessPath}")
    private String staticAccessPath;

    @Value("${file.uploadFolder}")
    private String uploadFolder;

//    @Value("${file.previewPDF}")
//    private String previewPDF;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+uploadFolder);
//        registry.addResourceHandler(staticAccessPath).addResourceLocations("file:"+previewPDF);
    }
}