package org.binchoo.study.spring.aws.rds.repository;

import org.binchoo.study.spring.aws.rds.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName : org.binchoo.study.spring.aws.rds.repository
 * fileName : PersonRepository
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
}
