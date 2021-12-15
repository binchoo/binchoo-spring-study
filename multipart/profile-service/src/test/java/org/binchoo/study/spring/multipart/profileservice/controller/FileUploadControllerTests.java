package org.binchoo.study.spring.multipart.profileservice.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.binchoo.study.spring.multipart.profileservice.config.WebConfig;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Part;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringJUnitWebConfig(classes = {WebConfig.class})
public class FileUploadControllerTests {

    MockMvc mvc;

    FileUploadController controller = new FileUploadController();

    MockPart mockPart = new MockPart("file", "filename.png", "file".getBytes());
    MockPart mockJsonPart = new MockPart("user", "{\"name\": \"jaebin-joo\", \"age\":\"11\"}".getBytes());
    MockMultipartFile mockFile = new MockMultipartFile("file", "filename.png", "image/png", "file".getBytes());

    @BeforeEach
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Order(1)
    @DisplayName("params + Part -> params + Part")
    @Test
    void bindParamsAndPart() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/param/part")
                        .part(mockPart)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().isOk())
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(2)
    @DisplayName("params + File -> params + MultipartFile")
    @Test
    void bindParamsAndFile() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/param/multipartfile")
                        .file(mockFile)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().isOk())
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }


    @Order(3)
    @DisplayName("params + Part -> VO + Part")
    @Test
    void bindVOAndPart() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/vo/part")
                        .part(mockPart)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().isOk())
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(4)
    @DisplayName("params + File -> VO + MultipartFile")
    @Test
    void bindVOAndFile() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/vo/multipartfile")
                        .file(mockFile)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().isOk())
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(5)
    @DisplayName("params + Part -> VO Having Part (Fails)")
    @Test
    void bindVOHavingPart() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/vopart")
                        .part(mockPart)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().is4xxClientError()) // StandardMultipartFile 타입을 Part로 변환할 수 없기 때문에
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(6)
    @DisplayName("params + File -> VO Having MultipartFile")
    @Test
    void bindVOHavingFile() throws Exception {
        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/vomultipartfile")
                        .file(mockFile)
                        .param("name", "jaebin-joo")
                        .param("age", "11"))
                    .andExpect(status().isOk())
                    .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(1);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(7)
    @DisplayName("json-part + Part -> VO + Part")
    @Test
    void bindJsonAndPart() throws Exception {
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/json-part/part")
                        .part(mockPart)
                        .part(mockJsonPart))
                        .andExpect(status().isOk())
                        .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(2);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Order(8)
    @DisplayName("json-part + File -> VO + MultipartFile")
    @Test
    void bindJsonAndFile() throws Exception {
        mockJsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        MultipartHttpServletRequest request = (MultipartHttpServletRequest)
                mvc.perform(multipart("/json-part/multipartfile")
                        .file(mockFile)
                        .part(mockJsonPart))
                        .andExpect(status().isOk())
                        .andReturn().getRequest();

        assertThat(request.getParts().size()).isEqualTo(2);
        assertThat(request.getMultiFileMap().size()).isEqualTo(0);
    }

    @Controller
    private class FileUploadController {

        @PostMapping("/param/part")
        public ResponseEntity bindParamsAndPart(@RequestParam("name") String name,
                                                @RequestParam("age") int age, @RequestPart(value = "file") Part file){
            return ResponseEntity.ok().build();
        }

        @PostMapping("/param/multipartfile")
        public ResponseEntity bindParamsAndFile(@RequestParam("name") String name,
                                                @RequestParam("age") int age, @RequestPart(value = "file") MultipartFile file){
            return ResponseEntity.ok().build();
        }

        @PostMapping("/vo/part")
        public ResponseEntity bindVOAndPart(UserVO userVo, @RequestPart(value = "file") Part file){
            return ResponseEntity.ok().build();
        }

        @PostMapping("/vo/multipartfile")
        public ResponseEntity bindVOAndFile(UserVO userVo, @RequestPart(value = "file") MultipartFile file){
            return ResponseEntity.ok().build();
        }

        @PostMapping("/vopart")
        public ResponseEntity bindVOHavingPart(UserHavingPartVO userVo){
            if (userVo.getFile() == null)
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().build();
        }

        @PostMapping("/vomultipartfile")
        public ResponseEntity bindVOHavingFile(UserHavingFileVO userVo){
            if (userVo.getFile() == null)
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok().build();
        }

        @PostMapping("/json-part/part")
        public ResponseEntity bindJsonAndPart(@RequestPart(value = "user") UserVO user, @RequestPart(value = "file") Part file){
            return ResponseEntity.ok().build();
        }

        @PostMapping("/json-part/multipartfile")
        public ResponseEntity bindJsonAndFile(@RequestPart(value = "user") UserVO user, @RequestPart(value = "file") MultipartFile file){
            return ResponseEntity.ok().build();
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor // jackson-databind objectMapper 사용을 위해
    @AllArgsConstructor
    private static class UserVO {
        private String name;
        private int age;
    }

    @Setter
    @Getter
    private static class UserHavingPartVO extends UserVO {
        private Part file;
        public UserHavingPartVO(String name, int age, Part file) {
            super(name, age);
            this.file = file;
        }
    }

    @Setter
    @Getter
    private static class UserHavingFileVO extends UserVO {
        private MultipartFile file;
        public UserHavingFileVO(String name, int age, MultipartFile file) {
            super(name, age);
            this.file = file;
        }
    }
}