package org.binchoo.study.spring.multipart.profileservice.init;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.binchoo.study.spring.multipart.profileservice.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Slf4j
@Component
public class DBInitializer {

    @Autowired
    UserProfileRepository repository;

    @PostConstruct
    private void setupTestData() {
        LocalDateTime birthDate = LocalDateTime.of(1996, 6, 15, 0, 0);
        UserProfile profile = null;
        try {
            profile = UserProfile.builder()
                    .birthDate(Timestamp.valueOf(birthDate))
                    .id("jbinchoo")
                    .name("재빈 주")
                    .phoneNumber("010-3452-9510")
                    .photo(loadPhoto("images/profile_image.png"))
                    .build();
            repository.save(profile);
            log.debug("기본 프로필 저장을 성공했습니다.");
            log.debug(repository.findById("jbinchoo").toString());
        } catch (IOException e) {
            e.printStackTrace();
            log.debug("기본 프로필 저장에 실패했습니다.");
        }
    }

    private byte[] loadPhoto(String filePathUnderClassPath) throws IOException {
        ClassPathResource imageResource = new ClassPathResource(filePathUnderClassPath);
        ImageInputStream imageInputStream = new FileImageInputStream(imageResource.getFile());
        byte[] photoBytes = new byte[(int)imageInputStream.length()];
        imageInputStream.read(photoBytes, 0, photoBytes.length);
        return photoBytes;
    }
}
