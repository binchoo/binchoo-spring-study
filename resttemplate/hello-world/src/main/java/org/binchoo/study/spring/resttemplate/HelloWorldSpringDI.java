package org.binchoo.study.spring.resttemplate;

import org.binchoo.study.spring.resttemplate.decoupled.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldSpringDI {
    public static void main(String... args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext
           ("spring/app-context.xml");

        MessageRenderer mr = ctx.getBean("renderer", MessageRenderer.class);
        mr.render();
    }
}
