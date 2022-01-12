package org.binchoo.study.spring.aws.rds.service;

import lombok.extern.slf4j.Slf4j;
import org.binchoo.study.spring.aws.rds.entity.Person;
import org.binchoo.study.spring.aws.rds.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

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
public class DBTester {

    @Autowired
    private PersonRepository repository;

    @Autowired
    private TaskExecutor taskExecutor;

    @PostConstruct
    public void doTest() {
        taskExecutor.execute(()-> init(100));
        keepRead(50, 10);
    }

    private void init(int rows) {
        List<Person> persons = new LinkedList<Person>();

        log.info("DB 초기화 시작");
        for(int i = 0; i < rows; i++) {
            persons.add(
                    Person.builder().firstName("JaeBin " + i).lastName("Joo").build());
        }
        repository.saveAll(persons);

        log.info("DB 초기화 완료");
    }

    private void keepRead(int count, long interval) {
        for(int i = 0; i < count; i++) {
            try {
                taskExecutor.execute(()-> read());
                Thread.sleep(interval);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void read() {
        log.info("읽기 시도");
        List<Person> persons = repository.findAll();
        persons.stream().forEach((it)->{
           log.info(persons.toString());
        });
    }
}
