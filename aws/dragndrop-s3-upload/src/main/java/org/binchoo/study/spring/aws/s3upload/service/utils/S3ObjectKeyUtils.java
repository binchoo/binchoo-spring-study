package org.binchoo.study.spring.aws.s3upload.service.utils;

import java.io.File;
import java.util.UUID;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.service.utils
 * fileName : FileToS3ObjectKey
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 */
public class S3ObjectKeyUtils {

    public static String keyFromUserName(String userName, File file) {
        final String fileName = file.getName();
        final String ext = fileName.substring(fileName.lastIndexOf('.'));
        validateFileExtension(ext);
        return userName + "/" + UUID.randomUUID() + ext;
    }

    private static void validateFileExtension(String fileExtension) {
        assert (".jpg".equals(fileExtension) || ".jpeg".equals(fileExtension) || ".png".equals(fileExtension));
    }
}
