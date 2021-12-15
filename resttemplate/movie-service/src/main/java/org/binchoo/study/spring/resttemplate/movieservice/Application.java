package org.binchoo.study.spring.resttemplate.movieservice;

import org.binchoo.study.spring.resttemplate.movieservice.config.ClientConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ClientConfig.class);
        System.in.read();
    }
}