package org.binchoo.study.spring.resttemplate.annotated;

import org.binchoo.study.spring.resttemplate.decoupled.MessageRenderer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by iuliana.cosmina on 1/28/17.
 */
public class HelloWorldSpringAnnotated {

    public static void main(String... args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext
                (HelloWorldConfiguration.class);
        MessageRenderer mr1 = ctx.getBean("renderer", MessageRenderer.class);
        MessageRenderer mr2 = ctx.getBean("renderer", MessageRenderer.class);
        mr1.render();
        mr2.render();

        System.out.println("render the same?  " + (mr1 == mr2) );
        System.out.println("provider the same?  " + (mr1.getMessageProvider() == mr2.getMessageProvider()) );
    }
}
