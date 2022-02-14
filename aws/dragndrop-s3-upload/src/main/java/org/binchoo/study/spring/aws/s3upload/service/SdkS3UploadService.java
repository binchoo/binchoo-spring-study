package org.binchoo.study.spring.aws.s3upload.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;
import org.binchoo.study.spring.aws.s3upload.service.utils.S3ObjectKeyUtils;

import java.io.File;
import java.util.Optional;

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
    private final ObjectMetadata aes256metadata;

    public SdkS3UploadService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.aes256metadata = new ObjectMetadata();
        aes256metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
    }

    @Override
    public Optional<S3ObjectUrlDto> uploadObject(File file, String userName) {
        final String key = S3ObjectKeyUtils.keyFromUserName(userName, file);
        final PutObjectRequest request = new PutObjectRequest(bucketName, key, file).withMetadata(aes256metadata);
        final PutObjectResult result = amazonS3.putObject(request);

        S3ObjectUrlDto dto = null;
        if (result != null) {
            final String objectUrl = amazonS3.getUrl(bucketName, key).toString();
            dto = S3ObjectUrlDto.builder().objectUrl(objectUrl).build();
        }
        return Optional.ofNullable(dto);
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}
