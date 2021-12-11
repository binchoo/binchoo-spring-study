package org.binchoo.study.spring.resttemplate.annotated;

import org.binchoo.study.spring.resttemplate.decoupled.HelloWorldMessageProvider;
import org.binchoo.study.spring.resttemplate.decoupled.MessageProvider;
import org.binchoo.study.spring.resttemplate.decoupled.MessageRenderer;
import org.binchoo.study.spring.resttemplate.decoupled.StandardOutMessageRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * Created by iuliana.cosmina on 1/28/17.
 */
@Configuration
public class HelloWorldConfiguration {

    @Scope("prototype")
    @Bean
    public MessageProvider provider() {
        return new HelloWorldMessageProvider();
    }

    @Scope("prototype")
    @Bean
    public MessageRenderer renderer(){
        MessageRenderer renderer = new StandardOutMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
