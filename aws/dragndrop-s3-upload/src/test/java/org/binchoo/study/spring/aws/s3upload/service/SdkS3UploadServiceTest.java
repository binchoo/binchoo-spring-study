package org.binchoo.study.spring.aws.s3upload.service;

import com.amazonaws.services.s3.AmazonS3;
import org.binchoo.study.spring.aws.s3upload.config.AmazonS3Config;
import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.service
 * fileName : SdkS3UploadServiceTest
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 * issues : 클래스 패스 리소스에서 파일 객체를 얻는 방법.
 */

@SpringJUnitConfig({AmazonS3Config.class})
class SdkS3UploadServiceTest {

    @Autowired
    AmazonS3 amazonS3;

    SdkS3UploadService sdkS3UploadService;
    File testFile;

    @BeforeEach
    public void init() throws IOException {
        sdkS3UploadService = new SdkS3UploadService(amazonS3);
        sdkS3UploadService.setBucketName("dragndrop-s3-upload");
        testFile = new ClassPathResource("a.jpg").getFile();
    }

    @Test
    void uploadJpgObject()  {
        Optional<S3ObjectUrlDto> dto = sdkS3UploadService.uploadObject(testFile, "binchoo");
        assertThat(dto.isPresent());
        dto.ifPresent(it-> {
            assertThat(it.getObjectUrl()).contains("binchoo");
            System.out.println("url =" + it.getObjectUrl());
        });
    }
}