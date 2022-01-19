package com.rutkoski.orders.kafka;

import com.rutkoski.orders.entities.Person;
import com.rutkoski.orders.entities.Product;
import com.rutkoski.orders.repositories.PersonRepository;
import com.rutkoski.orders.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class BrokerListener {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProductRepository productRepository;

    @KafkaListener(id = "order_person", topics = "${topics.person}")
    public void personTopicListener(Event<Person> event) {
        Person entity = event.getEntity();
        if(event.getOperation() == OperationEnum.PERSIST) {
            personRepository.save(entity);
        }else if(event.getOperation() == OperationEnum.DELETE){
            personRepository.delete(entity);
        }
    }

    @KafkaListener(id = "order_products", topics = "${topics.products}")
    public void productsTopicListener(Event<Product> event) {
        Product entity = event.getEntity();
        if(event.getOperation() == OperationEnum.PERSIST) {
            productRepository.save(entity);
        }else if(event.getOperation() == OperationEnum.DELETE){
            productRepository.delete(entity);
        }
    }
}
