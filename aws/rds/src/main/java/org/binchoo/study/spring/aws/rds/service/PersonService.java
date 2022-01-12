package org.binchoo.study.spring.aws.rds.service;

import org.binchoo.study.spring.aws.rds.entity.Person;

import java.util.List;

/**
 * packageName : org.binchoo.study.spring.aws.rds.service
 * fileName : PersonService
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */
public interface PersonService {
    List<Person> findAll();
    Person findById(Long id);
    void save(Person person);
}
