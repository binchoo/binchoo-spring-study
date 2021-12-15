package org.binchoo.study.spring.multipart.profileservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.binchoo.study.spring.multipart.profileservice.config.WebConfig;
import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.binchoo.study.spring.multipart.profileservice.service.UserProfileService;
import org.h2.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig(classes = {WebConfig.class})
class UserProfileControllerTests {

    private MockMvc mvc;

    @Autowired
    UserProfileController controller;

    @Autowired
    UserProfileService service;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
       this.mvc = MockMvcBuilders.standaloneSetup(controller).build();
       service.save(createTestProfile());
    }

    private UserProfile createTestProfile() {
        LocalDateTime birthDate = LocalDateTime.of(1996, 06, 15, 0, 0, 0);
        UserProfile testProfile = UserProfile.builder()
                .id("wnwoqls2")
                .birthDate(Timestamp.valueOf(birthDate))
                .name("주재빈")
                .phoneNumber("010-3452-9510")
                .photo(null)
                .build();
        return testProfile;
    }

    @Test
    public void profileViewTest() throws Exception {
        //given
        final String id = "wnwoqls2";
        //when
        mvc.perform(get("/profile/" + id))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void profileViewNotExistsTest() throws Exception {
        //given
        final String id = "garbage";
        //when
        mvc.perform(get("/profile/" + id))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void updateProfilePhotoNotExistsTest() throws Exception {
        //given
        UserProfile newProfile = createTestProfile();
        newProfile.setName("빈츄");
        //when
        mvc.perform(multipart("/profile/" + newProfile.getId())
                .param("name", newProfile.getName())
                .param("phoneNumber", newProfile.getPhoneNumber())
                .param("birthDate", new SimpleDateFormat("yyyy-MM-dd").format(newProfile.getBirthDate())))
                .andExpect(status().is3xxRedirection());
        //then
        final UserProfile savedProfile = service.findById(newProfile.getId()).orElseThrow(()-> new IllegalArgumentException());
        assertEquals("빈츄", savedProfile.getName());
    }

    @Test
    public void updateProfileViewTest() throws Exception {
        //given
        final String id = "wnwoqls2";
        //when
        mvc.perform(get("/profile/" + id + "?update"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void getPhotoNotExistsTest() throws Exception {
        //given
        UserProfile newProfile = createTestProfile();
        //then
        mvc.perform(get("/profile/" + newProfile.getId() + "/img"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPhotoExistsTest() throws Exception {
        //given
        String id = "jbinchoo";
        //when
        mvc.perform(get("/profile/" + id + "/img"))
                .andDo(print())
                .andExpect(status().isOk());
    }

     static class ObjectMapperUtils {

        public static String convertToFormUrlEncoded(ObjectMapper mapper, Object obj) {
            Map<String, Object> map = mapper.convertValue(obj, Map.class);
            String result = map.entrySet().stream()
                    .filter(e-> e.getValue() != null)
                    .map(e-> e.getKey() + "=" + StringUtils.urlEncode(e.getValue().toString()))
                    .collect(Collectors.joining("&"));
            return result;
        }
    }
}