package org.binchoo.study.spring.multipart.profileservice.config;

import org.binchoo.study.spring.multipart.profileservice.controller.vo.UserProfileVO;
import org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@EnableWebMvc
@ComponentScan(basePackages = {"org.binchoo.study.spring.multipart.profileservice"})
@Configuration
@Import(MustacheAutoConfiguration.class)
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/profile/jbinchoo");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/")
            .setCachePeriod(31556926);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new UserProfileVO.EntityConverter());
    }

    @Bean
    public StandardServletMultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
