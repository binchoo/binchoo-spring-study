package org.binchoo.study.spring.multipart.profileservice.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ToString(exclude = {"file"})
@Setter
@Getter
@AllArgsConstructor
public class UserWithPartVO {
    private String name;
    private int age;
    private List<MultipartFile> file;
}