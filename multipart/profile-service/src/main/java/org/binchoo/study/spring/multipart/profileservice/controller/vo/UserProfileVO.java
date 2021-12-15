package org.binchoo.study.spring.multipart.profileservice.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.binchoo.study.spring.multipart.profileservice.entity.UserProfile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
public class UserProfileVO {

    String id;

    String name;

    String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date birthDate;

    MultipartFile file;

    @NoArgsConstructor
    public static class EntityConverter implements Converter<UserProfileVO, UserProfile> {
        @Override
        public UserProfile convert(UserProfileVO source) {
            byte[] photoBytes = null;
            try {
                if (source.file != null && 0 != source.file.getSize() ) {
                    photoBytes = source.file.getBytes();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return UserProfile.builder()
                    .photo(photoBytes)
                    .id(source.id)
                    .name(source.name)
                    .phoneNumber(source.phoneNumber)
                    .birthDate(source.birthDate)
                    .build();
        }
    }
}