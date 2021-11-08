package com.rutkoski.orders.services;

import com.rutkoski.orders.entities.Order;
import com.rutkoski.orders.entities.OrderProduct;
import org.springframework.stereotype.Service;

@Service
public class OrderProductService {

    public boolean validate(OrderProduct entity){
        if(entity == null){
            return false;
        }
        if(entity.getProduct() == null){
            return false;
        }
        if(entity.getQuantity() == null || entity.getQuantity() <= 0.0){
            return false;
        }
        return true;
    }
}
