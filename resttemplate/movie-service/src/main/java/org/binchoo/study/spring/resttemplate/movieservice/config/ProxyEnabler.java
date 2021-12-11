package org.binchoo.study.spring.resttemplate.movieservice.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Profile("proxy")
@Component
public class ProxyEnabler {

    @PostConstruct
    void enableProxy() {
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "8888");
        System.setProperty("https.proxyPort", "8888");
        System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\wnwoq\\.jdks\\azul-1.8.0_275\\bin\\FiddlerKeystore");
        System.setProperty("javax.net.ssl.trustStorePassword", "Qkqhwktlr1!");
    }
}
