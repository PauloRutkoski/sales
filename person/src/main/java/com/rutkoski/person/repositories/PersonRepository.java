package com.rutkoski.person.repositories;

import com.rutkoski.person.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
