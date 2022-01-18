package com.rutkoski.orders.repositories;

import com.rutkoski.orders.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
