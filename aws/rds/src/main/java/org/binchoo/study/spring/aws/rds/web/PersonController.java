package org.binchoo.study.spring.aws.rds.web;

import org.binchoo.study.spring.aws.rds.entity.Person;
import org.binchoo.study.spring.aws.rds.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName : org.binchoo.study.spring.aws.rds.web
 * fileName : UserController
 * author : jbinchoo
 * date : 2022-01-12
 * description :
 */

@RestController
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/persons")
    public ResponseEntity getPersons() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/persons/add/{firstName}/{lastName}")
    public ResponseEntity addPerson(@PathVariable(name="firstName") String firstName,
                                    @PathVariable(name="lastName") String lastName) {
        Person p = Person.builder().firstName(firstName).lastName(lastName).build();
        service.save(p);
        return ResponseEntity.ok().build();
    }
}
