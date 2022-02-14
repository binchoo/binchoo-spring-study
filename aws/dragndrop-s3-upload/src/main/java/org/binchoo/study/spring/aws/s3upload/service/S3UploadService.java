package org.binchoo.study.spring.aws.s3upload.service;

import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;

import java.io.File;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.service
 * fileName : S3UploadService
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
public interface S3UploadService {
    S3ObjectUrlDto uploadObject(File file, String userName);
}
