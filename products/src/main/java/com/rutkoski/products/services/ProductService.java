package com.rutkoski.products.services;

import com.rutkoski.products.entities.Product;
import com.rutkoski.products.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Product load(Long id) {
        Optional<Product> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product persist(Product entity) {
        return repository.save(entity);
    }
}
