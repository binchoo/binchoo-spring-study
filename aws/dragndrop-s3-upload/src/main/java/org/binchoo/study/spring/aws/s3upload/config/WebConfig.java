package org.binchoo.study.spring.aws.s3upload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.config
 * fileName : LoginWebConfig
 * author : jbinchoo
 * date : 2022-02-15
 * description :
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login.html");
        registry.addViewController("/index").setViewName("main.html");
        registry.addRedirectViewController("/", "/index");
    }
}