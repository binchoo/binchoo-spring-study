package org.binchoo.study.spring.aws.rds.service;

import org.binchoo.study.spring.aws.rds.entity.Person;
import org.binchoo.study.spring.aws.rds.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName : org.binchoo.study.spring.aws.rds.service
 * fileName : JpaPersonService
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */
public class JpaPersonService implements PersonService {

    @Autowired
    private PersonRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Person findById(Long id) {
        return repository.findById(id).get();
    }

    @Transactional
    @Override
    public void save(Person person) {
        repository.save(person);
    }
}
