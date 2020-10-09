package com.JackZ.SpringBucks.Controller;

import com.JackZ.SpringBucks.Controller.request.NewOrderRequest;
import com.JackZ.SpringBucks.Controller.request.UpdateOrderStatusRequest;
import com.JackZ.SpringBucks.model.Coffee;
import com.JackZ.SpringBucks.model.CoffeeOrder;
import com.JackZ.SpringBucks.model.OrderState;
import com.JackZ.SpringBucks.service.CoffeeOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/order")
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder createOrder(@Valid @RequestBody NewOrderRequest request) {
        List<Coffee> coffees = new ArrayList<>();
        List<String> orders = request.getItems();
        orders.forEach(order -> coffees.add(Coffee.builder()
                .name(order).build()));
        CoffeeOrder order = coffeeOrderService.createOrder(request.getCustomer(), coffees);
        return order;
    }

    @GetMapping(path = "/{id}")
    public CoffeeOrder getOrderById(@PathVariable("id") Long id) {
        return coffeeOrderService.getOrderById(id);
    }

    @GetMapping(path = "/", params = "customer")
    public List<CoffeeOrder> getOrdersByCustomer(@RequestParam("customer") String customer) {
        return coffeeOrderService.getOrderByCustomer(customer);
    }

    @GetMapping(path = "/all")
    public List<CoffeeOrder> getAll() {
        return coffeeOrderService.findAll();
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public CoffeeOrder updateStatus(@Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderState newStatus = request.getStatus();
        CoffeeOrder preOrder = coffeeOrderService.getOrderById(request.getId());
        return coffeeOrderService.updateStatus(preOrder, newStatus);
    }
}
