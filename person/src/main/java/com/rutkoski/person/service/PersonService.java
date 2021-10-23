package com.rutkoski.person.service;

import com.rutkoski.person.entities.Person;
import com.rutkoski.person.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public Person load(Long id) {
        Optional<Person> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person persist(Person entity) {
        return repository.save(entity);
    }

}
