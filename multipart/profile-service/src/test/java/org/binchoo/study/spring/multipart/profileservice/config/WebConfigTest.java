package org.binchoo.study.spring.multipart.profileservice.config;

import org.h2.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mustache.MustacheResourceTemplateLoader;
import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import java.io.Reader;
import java.util.Locale;

@SpringJUnitWebConfig(classes = {WebConfig.class})
public class WebConfigTest {

    @Autowired
    MustacheViewResolver viewResolver;

    @Autowired
    MustacheResourceTemplateLoader templateLoader;

    @Test
    public void testMustacheLoader() throws Exception {
        Reader r = templateLoader.getTemplate("profile");
        String str = IOUtils.readStringAndClose(r, 1000);
        System.out.println(str);
    }

    @Test
    public void testViewResolver() throws Exception {
        viewResolver.resolveViewName("profile", new Locale("ko-KR"));
    }
}