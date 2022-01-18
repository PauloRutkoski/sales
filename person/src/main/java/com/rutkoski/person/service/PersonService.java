package com.rutkoski.person.service;

import com.rutkoski.person.kafka.Event;
import com.rutkoski.person.entities.Person;
import com.rutkoski.person.kafka.KafkaHelper;
import com.rutkoski.person.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;
    @Autowired
    private KafkaHelper helper;

    public boolean valid(Person entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            return false;
        }
        if (entity.getDocument() == null || entity.getDocument().length() < 4) {
            return false;
        }
        return true;
    }

    public Person load(Long id) {
        Optional<Person> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person persist(Person entity) {
        entity = repository.save(entity);
        helper.persistPersonEvent(entity);
        return entity;
    }

}
