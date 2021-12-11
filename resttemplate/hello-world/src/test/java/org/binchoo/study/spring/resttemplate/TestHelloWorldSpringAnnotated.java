package org.binchoo.study.spring.resttemplate;

import org.binchoo.study.spring.resttemplate.annotated.HelloWorldConfiguration;
import org.binchoo.study.spring.resttemplate.decoupled.MessageRenderer;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.Assert.assertNotNull;

/**
 * Created by iuliana.cosmina on 1/28/17.
 */
public class TestHelloWorldSpringAnnotated {

    @Test
    public void testHello() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext
                (HelloWorldConfiguration.class);
        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        assertNotNull(mr);
    }
}
