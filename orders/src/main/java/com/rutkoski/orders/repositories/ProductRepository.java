package com.rutkoski.orders.repositories;

import com.rutkoski.orders.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
