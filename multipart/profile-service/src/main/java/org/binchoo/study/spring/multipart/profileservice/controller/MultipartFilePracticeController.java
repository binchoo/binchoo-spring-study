package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.multipart.profileservice.controller.vo.UserWithMultipartFileVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Slf4j
@Controller
public class MultipartFilePracticeController {

    @RequestMapping(value = "/multipartfile", method = RequestMethod.POST)
    public ResponseEntity saveObjectAndImages(UserWithMultipartFileVO userVo) {
        log.info("UserWithMultipartFileVO 획득:" + userVo.toString());
        Optional.ofNullable(userVo.getFile())
                .ifPresent(images-> {
                    log.info("이미지를 " + images.size() + "개 얻었습니다.");
                    images.forEach(file -> {
                        log.info("이름: " + file.getResource().getFilename() + " 크기 :" + file.getSize());
                    });
                });
        return ResponseEntity.ok().build();
    }
}
