package com.JackZ.SpringBucks.service;

import com.JackZ.SpringBucks.model.Coffee;
import com.JackZ.SpringBucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee saveCoffee(String name, Money price) {
        Coffee coffee = Coffee.builder().name(name).price(price).build();
        log.info("New Coffe {}", coffee);
        return coffeeRepository.save(coffee);
    }

    public List<Coffee> getAllCoffee() {
        log.info("Get All Coffee.");
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee getCoffeeById(Long id) {
        log.info("Try find coffee by id: {}", id);
        Optional<Coffee> coffees = coffeeRepository.findById(id);
        return coffees.orElse(null);
    }

    public List<Coffee> getCoffeeByName(String name) {
        log.info("Try find coffee by name: {}", name);
        return coffeeRepository.findByName(name);
    }

    public boolean deleteCoffeeById(Long id) {
        boolean exists = coffeeRepository.existsById(id);
        if (!exists) return false;
        else coffeeRepository.deleteById(id);
        return true;
    }
}
