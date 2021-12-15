package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.binchoo.study.spring.multipart.profileservice.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = {WebConfig.class})
public class MultipartFilePracticeControllerTests {

    final static String URL = "/multipartfile";

    MockMvc mvc;

    @Autowired
    MultipartFilePracticeController controller;

    UserVO testUserVo;
    MockMultipartFile singleImage;
    List<MockMultipartFile> multiImages;

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

        String[] fileNames = {"image1.png", "image2.png", "image3.png"};
        byte[] fileBytes = "이미지 내용입니다.".getBytes();

        this.multiImages = Arrays.stream(fileNames)
                .map(it-> new MockMultipartFile("file", it, "image/png", fileBytes))
                .collect(Collectors.toList());

        this.singleImage = multiImages.get(0);
    }

    @DisplayName("객체를 멀티파트 전송했을 때")
    @Test
    public void sendObjectAndNoImageTest() throws Exception {
        // given
        testUserVo = new UserVO("jaebin-joo", 90);
        // when
        MvcResult result = mvc.perform(multipart(URL)
                    .characterEncoding("UTF-8")
                    .param("name", testUserVo.getName())
                    .param("age", String.valueOf(testUserVo.getAge())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) result.getRequest();
        // then
        assertNull(request.getPart("file"));
    }

    @DisplayName("객체와 한 이미지를 멀티파트 전송했을 때")
    @Test
    public void sendObjectAndImageTest() throws Exception {
        // given
        testUserVo = new UserVO("jaebin-joo", 91);
        // when
        MvcResult result = mvc.perform(multipart(URL)
                    .file(singleImage)
                    .characterEncoding("UTF-8")
                    .param("name", testUserVo.getName())
                    .param("age", String.valueOf(testUserVo.getAge())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        MultipartHttpServletRequest request = (MockMultipartHttpServletRequest) result.getRequest();
        // then
        assertNotNull(request.getPart("file"));
    }

    @DisplayName("객체와 여러 이미지를 멀티파트 전송했을 때")
    @Test
    public void sendObjectAndImagesTest() throws Exception {
        // given
        testUserVo = new UserVO("jaebin-joo", 92);
        // when
        MvcResult result = mvc.perform(multipart(URL)
                    .file(multiImages.get(0)).file(multiImages.get(1)).file(multiImages.get(2))
                    .characterEncoding("UTF-8")
                    .param("name", testUserVo.getName())
                    .param("age", String.valueOf(testUserVo.getAge())))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        MultipartHttpServletRequest request = (MultipartHttpServletRequest) result.getRequest();
        // then
        assertNotNull(request.getPart("file"));
    }

    @Getter
    @AllArgsConstructor
    private static class UserVO {
        private String name;
        private int age;
    }
}