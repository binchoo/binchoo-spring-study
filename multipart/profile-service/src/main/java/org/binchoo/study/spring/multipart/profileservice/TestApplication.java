package org.binchoo.study.spring.multipart.profileservice;

import org.binchoo.study.spring.multipart.profileservice.config.DataConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class TestApplication {
    public static void main(String[] args) throws IOException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
        System.in.read();
    }
}
