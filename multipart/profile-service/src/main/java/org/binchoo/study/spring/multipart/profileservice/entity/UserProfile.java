package org.binchoo.study.spring.multipart.profileservice.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_profile")
@Entity
public class UserProfile {

    @Id
    @Column(name = "ID")
    String id;

    @Column(name = "NAME")
    String name;

    @Column(name = "PHONE_NUMBER")
    String phoneNumber;

    @Temporal(TemporalType.DATE)
    @Column(name = "BIRTH_DATE")
    Date birthDate;

    @Basic(fetch = FetchType.LAZY)
    @Lob
    @Column(name = "PHOTO")
    byte[] photo;
}
