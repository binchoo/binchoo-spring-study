package org.binchoo.study.spring.resttemplate.decoupled;

public class HelloWorldMessageProvider implements MessageProvider {

    public HelloWorldMessageProvider(){
        System.out.println(" --> HelloWorldMessageProvider: 생성자가 호출됨");
    }

    @Override
    public String getMessage() {
        return "Hello World!";
    }
}
