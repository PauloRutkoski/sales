package com.rutkoski.orders.controllers;

import com.rutkoski.orders.entities.Order;
import com.rutkoski.orders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> list = service.findAll();
        if (list == null || list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id) {
        Order entity = service.load(id);
        if (entity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    public ResponseEntity<Order> insert(@RequestBody Order entity) {
        boolean valid = service.validate(entity);
        if(!valid){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity = service.persist(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Order> update(@RequestBody Order entity, @PathVariable Long id) {
        if(!service.validate(entity)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        entity.setId(id);
        entity = service.persist(entity);
        return ResponseEntity.ok(entity);
    }
}
