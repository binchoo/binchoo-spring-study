package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.multipart.profileservice.controller.vo.UserWithPartVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@Slf4j
@RequestMapping("/multipart")
@Controller
public class MultipartPracticeController {

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity saveObjectAndImages(UserWithPartVO userWithPartVo) {
        log.info("UserVO를 획득:" + userWithPartVo.toString());
        Optional.ofNullable(userWithPartVo.getFile())
                .ifPresent(images-> {
                    log.info("이미지를 " + images.size() + "개 얻었습니다.");
                    images.forEach(part -> {
                        log.info("이름: " + part.getResource().getFilename() + " 크기 :" + part.getSize());
                    });
                });
        return ResponseEntity.ok().build();
    }
}
