package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.multipart.profileservice.controller.vo.UserWithPartVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import javax.servlet.http.Part;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class PartPracticeController {

    @RequestMapping(value = "/part", method = RequestMethod.POST)
    public ResponseEntity saveObjectAndImages(UserWithPartVO userVo) {
        log.info("UserVO를 획득:" + userVo.toString());
        Optional.ofNullable(userVo.getFile())
                .ifPresent(images-> {
                    log.info("이미지를 " + images.size() + "개 얻었습니다.");
                    images.forEach(part -> {
                        log.info("이름: " + part.getName() + " 크기 :" + part.getSize());
                    });
                });
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "/paramAndPart", method = RequestMethod.POST)
    public ResponseEntity saveParamsAndImages(@RequestParam("name") String name, @RequestParam("age") int age,
                                              @RequestPart(value = "file", required = false) List<Part> files) {
        log.info("User 정보를 획득" + name + ", " + String.valueOf(age));
        Optional.ofNullable(files)
                .ifPresent(images-> {
                    log.info("이미지를 " + images.size() + "개 얻었습니다.");
                    images.forEach(part -> {
                        log.info("이름: " + part.getName() + " 크기 :" + part.getSize());
                    });
                });
        return ResponseEntity.ok().build();
    }
}
