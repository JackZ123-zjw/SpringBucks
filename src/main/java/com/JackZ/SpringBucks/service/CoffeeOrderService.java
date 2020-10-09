package com.JackZ.SpringBucks.service;

import com.JackZ.SpringBucks.model.Coffee;
import com.JackZ.SpringBucks.model.CoffeeOrder;
import com.JackZ.SpringBucks.model.OrderState;
import com.JackZ.SpringBucks.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder createOrder(String customer, List<Coffee> coffees) {
        CoffeeOrder coffeeOrder = CoffeeOrder.builder()
                .customer(customer)
                .items(coffees)
                .status(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(coffeeOrder);
        log.info("New order created: {}", coffeeOrder);
        return coffeeOrder;
    }

    public CoffeeOrder getOrderById(Long id) {
        CoffeeOrder coffeeOrder = coffeeOrderRepository.getOne(id);
        log.info("Find order by id: {}", coffeeOrder);
        return coffeeOrder;
    }

    public List<CoffeeOrder> getOrderByCustomer(String customer) {
        List<CoffeeOrder> coffeeOrders = coffeeOrderRepository.findByCustomer(customer);
        coffeeOrders.forEach(coffeeOrder -> log.info("Find by customer: {}", coffeeOrder));
        return coffeeOrders;
    }

    public CoffeeOrder updateStatus(CoffeeOrder coffeeOrder, OrderState newStatus) {
        OrderState nowStatus = coffeeOrder.getStatus();
        if (nowStatus.compareTo(newStatus) >= 0) {
            log.warn("Wrong status: {}", coffeeOrder.getStatus());
            return null;
        }
        coffeeOrder.setStatus(newStatus);
        coffeeOrderRepository.save(coffeeOrder);
        log.info("Order status updated: {}", coffeeOrder);
        return coffeeOrder;
    }

    public List<CoffeeOrder> findAll() {
        List<CoffeeOrder> orders = coffeeOrderRepository.findAll();
        log.info("Find {} orders.", orders.size());
        orders.forEach(order -> log.info("{}", order));
        return orders;
    }
}
