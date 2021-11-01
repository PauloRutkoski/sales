package com.rutkoski.person.controllers;

import com.rutkoski.person.entities.Person;
import com.rutkoski.person.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/person")
@Validated
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        List<Person> list = service.findAll();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Person entity = service.load(id);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<Person> insert(@RequestBody Person entity) {
        boolean valid = service.valid(entity);
        if(!valid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity = service.persist(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping
    public ResponseEntity<Person> update(@RequestBody Person entity) {
        if(!service.valid(entity)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity = service.persist(entity);
        return ResponseEntity.ok(entity);
    }

}
