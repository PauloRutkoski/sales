package com.rutkoski.orders.kafka;

import com.rutkoski.orders.entities.Person;
import com.rutkoski.orders.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class BrokerListener {
    @Autowired
    private PersonRepository personRepository;

    @KafkaListener(id = "order_person", topics = "${topics.person}")
    public void testsTopicListener(Event<Person> event) {
        Person entity = event.getEntity();
        if(event.getOperation() == OperationEnum.PERSIST) {
            personRepository.save(entity);
        }else if(event.getOperation() == OperationEnum.DELETE){
            personRepository.delete(entity);
        }
    }
}
