package org.binchoo.study.spring.aws.rds.entity;

import lombok.*;

import javax.persistence.*;

/**
 * packageName : org.binchoo.study.spring.aws.rds.entity
 * fileName : User
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */

@ToString
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "person")
@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;
}
