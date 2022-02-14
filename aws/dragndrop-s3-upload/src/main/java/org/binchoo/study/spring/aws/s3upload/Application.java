package org.binchoo.study.spring.aws.s3upload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload
 * fileName : Application
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
