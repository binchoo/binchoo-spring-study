package org.binchoo.study.spring.aws.rds.config;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.aws.rds.entity.Person;
import org.binchoo.study.spring.aws.rds.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

/**
 * packageName : org.binchoo.study.spring.aws.rds.config
 * fileName : DBInitializer
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */

@Slf4j
@Component
public class DBInitializer {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private TaskExecutor taskExecutor;

    @PostConstruct
    public void launchInitialization() {
        taskExecutor.execute(()-> init(10));
        log.info("DB 초기화 시작");
    }

    public void init(int rows) {
        List<Person> persons = new LinkedList<Person>();
        for(int i = 0; i < rows; i++) {
            persons.add(
                    Person.builder().firstName("JaeBin " + i).lastName("Joo").build());
        }

        repository.saveAll(persons);

        for (int i = 0; i < 10; i++)
            testRead();
    }

    @Transactional(readOnly = true)
    private void testRead() {
        List<Person> persons = repository.findAll();
        persons.stream().forEach((it)->{
           log.info(persons.toString());
        });
    }
}
