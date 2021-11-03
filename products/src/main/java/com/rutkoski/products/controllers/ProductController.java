package com.rutkoski.products.controllers;

import com.rutkoski.products.entities.Product;
import com.rutkoski.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<Product>> findAll() {
        List<Product> list = service.findAll();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Product entity = service.load(id);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<Product> insert(@RequestBody Product entity) {
        if(!service.validate(entity)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity = service.persist(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping
    public ResponseEntity<Product> update(@RequestBody Product entity) {
        if(!service.validate(entity)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity = service.persist(entity);
        return ResponseEntity.ok(entity);
    }
}
