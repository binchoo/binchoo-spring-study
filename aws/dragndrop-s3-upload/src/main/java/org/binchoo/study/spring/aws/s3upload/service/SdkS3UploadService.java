package org.binchoo.study.spring.aws.s3upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;
import org.binchoo.study.spring.aws.s3upload.service.utils.S3ObjectKeyUtils;

import java.io.File;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.service
 * fileName : SdkS3UploadService
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 * issues : SSE-S3(AES256) 암호화를 쓰기 위해 메타데이터를 사용하기.
 */

public class SdkS3UploadService implements S3UploadService {

    private String bucketName;

    private final AmazonS3 amazonS3;

    private final ObjectMetadata metadata;

    public SdkS3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.metadata = new ObjectMetadata();
        metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
    }

    @Override
    public S3ObjectUrlDto uploadObject(File file, String userName) {
        final String key = S3ObjectKeyUtils.keyFromUserName(userName, file);
        final PutObjectRequest request = new PutObjectRequest(bucketName, key, file).withMetadata(metadata);

        PutObjectResult result = amazonS3.putObject(request);
        if (result != null) {
            String objectUrl = amazonS3.getUrl(bucketName, key).toString();
            return S3ObjectUrlDto.builder().objectUrl(objectUrl).build();
        }
        return null;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
