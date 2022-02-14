package org.binchoo.study.spring.aws.s3upload.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;
import org.binchoo.study.spring.aws.s3upload.service.S3UploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.controller
 * fileName : ObjectUploadController
 * author : jbinchoo
 * date : 2022-02-14
 * description :
 * issues: 인증된 사용자를 주입받는 방법. MultipartFile을 File로 변환하는 방법.
 */

@Setter
@Getter
@AllArgsConstructor
@RestController
public class S3ObjectUploadController {

    private S3UploadService uploadService;

    @PostMapping("/s3-upload")
    public ResponseEntity<S3ObjectUrlDto> s3ObjectUpload(@RequestPart("file") @NotNull MultipartFile part,
                                                         @AuthenticationPrincipal Principal principal) throws IOException {
        Optional<S3ObjectUrlDto> optional = uploadService.uploadObject(convertMultipartToFile(part), principal.getName());
        if (optional.isPresent())
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private File convertMultipartToFile(MultipartFile part) throws IOException {
        final File file = new File(part.getOriginalFilename());
        part.transferTo(file);
        return file;
    }
}
