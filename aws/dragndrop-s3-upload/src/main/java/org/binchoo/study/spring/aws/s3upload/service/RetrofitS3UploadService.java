package org.binchoo.study.spring.aws.s3upload.service;

import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;

import java.io.File;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.service
 * fileName : RetrofitS3UploadService
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
public class RetrofitS3UploadService implements S3UploadService {
    @Override
    public S3ObjectUrlDto uploadObject(File file, String userName) {
        return null;
    }
}
