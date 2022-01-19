package com.rutkoski.products.services;

import com.rutkoski.products.entities.Product;
import com.rutkoski.products.kafka.Event;
import com.rutkoski.products.kafka.KafkaHelper;
import com.rutkoski.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private KafkaHelper helper;

    public boolean validate(Product entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getReference() == null || entity.getReference().isEmpty()) {
            return false;
        }
        if (entity.getName() == null || entity.getName().isEmpty()) {
            return false;
        }
        if (entity.getBrand() == null || entity.getBrand().isEmpty()) {
            return false;
        }
        if (entity.getPrice() == null || entity.getPrice() <= 0.0) {
            return false;
        }
        return true;
    }

    public Product load(Long id) {
        Optional<Product> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product persist(Product entity) {
        entity = repository.save(entity);
        helper.persistProductEvent(entity);
        return entity;
    }
}
