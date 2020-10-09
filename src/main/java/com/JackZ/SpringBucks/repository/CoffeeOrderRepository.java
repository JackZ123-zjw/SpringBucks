package com.JackZ.SpringBucks.repository;

import com.JackZ.SpringBucks.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {

    List<CoffeeOrder> findByCustomer(String customer);
}
