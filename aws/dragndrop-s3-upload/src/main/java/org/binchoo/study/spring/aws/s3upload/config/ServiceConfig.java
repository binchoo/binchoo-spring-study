package org.binchoo.study.spring.aws.s3upload.config;

import com.amazonaws.services.s3.AmazonS3;
import org.binchoo.study.spring.aws.s3upload.service.S3UploadService;
import org.binchoo.study.spring.aws.s3upload.service.SdkS3UploadService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.config
 * fileName : ServiceConfig
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
@Configuration
public class ServiceConfig {

    @DependsOn("amazonS3")
    @Bean
    S3UploadService s3UploadService(AmazonS3 amazonS3) {
        SdkS3UploadService service = new SdkS3UploadService(amazonS3);
        service.setBucketName("dragndrop-s3-upload");
        return service;
    }
}
