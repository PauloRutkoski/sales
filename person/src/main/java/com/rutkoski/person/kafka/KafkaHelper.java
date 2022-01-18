package com.rutkoski.person.kafka;

import com.rutkoski.person.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaHelper {
    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;
    @Value(value = "${topics.person}")
    private String personTopic;

    public void persistPersonEvent(Person entity){
        Event event = new Event(OperationEnum.PERSIST, entity);
        kafkaTemplate.send(personTopic, event);
    }
}
