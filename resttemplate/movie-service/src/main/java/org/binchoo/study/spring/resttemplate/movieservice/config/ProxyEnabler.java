package org.binchoo.study.spring.resttemplate.movieservice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

@Profile("proxy")
@Component
public class ProxyEnabler {

    @PostConstruct
    void enableProxy() {
        Properties props = new Properties();
        try {
            props.load(new ClassPathResource("proxy-config.properties").getInputStream());
            System.setProperties(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
