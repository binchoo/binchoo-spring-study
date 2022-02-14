package org.binchoo.study.spring.aws.s3upload.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.dto
 * fileName : S3ObjectUrlDto
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
@Getter
@Builder
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class S3ObjectUrlDto {
    private final String objectUrl;
}
