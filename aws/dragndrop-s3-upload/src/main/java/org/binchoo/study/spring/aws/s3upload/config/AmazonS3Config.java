package org.binchoo.study.spring.aws.s3upload.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.PropertiesFileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.config
 * fileName : AmazonS3Config
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
@Configuration
public class AmazonS3Config {

    @Bean
    AmazonS3 amazonS3() throws IOException {
        AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder.standard();
        clientBuilder.setCredentials(credentialsProvider());
        clientBuilder.setRegion("ap-northeast-1");
        return clientBuilder.build();
    }

    @Bean
    AWSCredentialsProvider credentialsProvider() throws IOException {
        return new PropertiesFileCredentialsProvider(
                new ClassPathResource("aws.properties").getFile().getAbsolutePath());
    }
}
