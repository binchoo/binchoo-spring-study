package org.binchoo.study.spring.aws.s3upload.controller;

import org.apache.http.entity.ContentType;
import org.binchoo.study.spring.aws.s3upload.dto.S3ObjectUrlDto;
import org.binchoo.study.spring.aws.s3upload.service.S3UploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName : org.binchoo.study.spring.aws.s3upload.controller
 * fileName : ObjectUploadControllerTest
 * author : jbinchoo
 * date : 2022-02-15
 * description :
 * issues : csrf 요청을 목하기. 유저 인증을 목하기. 예외 발생을 기대하기.
 */

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class S3ObjectUploadControllerTest {

    final static String REQUEST_URL = "/s3-upload";
    final static String MOCK_RETURN_URL = "https://dragndrop-s3-upload.s3.ap-northeast-1.amazonaws.com/binchoo/test.png";

    @Autowired
    S3ObjectUploadController controller;
    @Autowired
    MockMvc mockMvc;

    S3UploadService s3UploadService;
    MockMultipartFile multipartFile;

    @BeforeEach
    public void init() {
        s3UploadService = mock(S3UploadService.class);
        when(s3UploadService.uploadObject(any(), any())).thenReturn(
                S3ObjectUrlDto.builder().objectUrl(MOCK_RETURN_URL).build());

        controller.setUploadService(s3UploadService);
        multipartFile = new MockMultipartFile("file",
                "a.jpg", ContentType.IMAGE_JPEG.getMimeType(), "1234".getBytes());
    }

    @WithMockUser(username = "binchoo", password = "1234")
    @Test
    void s3ObjectUpload() throws Exception {
        mockMvc.perform(multipart(REQUEST_URL)
                .file(multipartFile)
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.object_url").value(MOCK_RETURN_URL))
            .andDo(print());
    }

    @WithMockUser(username = "binchoo", password = "1234")
    @Test
    void s3ObjectUploadWithNullPart() {
        assertThrows(IllegalArgumentException.class, ()-> {
            mockMvc.perform(multipart(REQUEST_URL)
                    .file(null)
                    .with(csrf()))
                    .andExpect(status().isInternalServerError())
                    .andDo(print());
        });
    }

    @WithMockUser(username = "binchoo", password = "1234")
    @Test
    void s3ObjectUploadWithoutCsrfToken() throws Exception {
        mockMvc.perform(multipart(REQUEST_URL)
                .file(multipartFile))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void s3ObjectUploadWithoutLogin() throws Exception {
        mockMvc.perform(multipart(REQUEST_URL)
                .file(multipartFile)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andDo(print());
    }
}