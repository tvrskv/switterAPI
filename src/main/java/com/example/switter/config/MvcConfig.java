package com.example.switter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// конфигурация веб слоя. система авторизации
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    // раздача контента (загрузка файлов)
    @Value ("${upload.path}")
    private String uploadPath;

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("file://" + uploadPath + "/"); // file:// - место в файловой системе, uploadPath - абсолютный путь, который мы передали в application
        // при обращении по этому пути ресурсы будут искаться в корне проекта
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}