package com.rutkoski.orders.services;

import com.rutkoski.orders.entities.Order;
import com.rutkoski.orders.entities.OrderProduct;
import com.rutkoski.orders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderProductService orderProductService;

    public boolean validate(Order entity){
        if(entity == null){
            return false;
        }
        if(entity.getPerson() == null){
            return false;
        }
        if(!this.validateProducts(entity.getProducts())){
            return false;
        }

        return true;
    }

    private boolean validateProducts(List<OrderProduct> products){
        if(products== null || products.isEmpty()){
            return false;
        }
        for(OrderProduct product : products){
            boolean valid = orderProductService.validate(product);
            if(!valid){
                return false;
            }
        }
        return true;
    }

    public Order load(Long id) {
        Optional<Order> entity = repository.findById(id);
        return entity.orElse(null);
    }

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order persist(Order entity) {
        if(entity.getProducts() != null) {
            for (OrderProduct it : entity.getProducts()) {
                it.setOrder(entity);
            }
        }
        return repository.save(entity);
    }
}
