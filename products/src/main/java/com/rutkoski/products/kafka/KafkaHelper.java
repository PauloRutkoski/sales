package com.rutkoski.products.kafka;

import com.rutkoski.products.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaHelper {
    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;
    @Value(value = "${topics.products}")
    private String productsTopic;

    public void persistProductEvent(Product entity){
        Event event = new Event(OperationEnum.PERSIST, entity);
        kafkaTemplate.send(productsTopic, event);
    }
}
