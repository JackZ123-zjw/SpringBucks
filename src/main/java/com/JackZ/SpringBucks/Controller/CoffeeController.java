package com.JackZ.SpringBucks.Controller;

import com.JackZ.SpringBucks.Controller.request.NewCoffeeRequest;
import com.JackZ.SpringBucks.model.Coffee;
import com.JackZ.SpringBucks.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(name = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(name = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest request) {
        return coffeeService.saveCoffee(request.getName(), request.getPrice());
    }

    @PostMapping(name = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));){

                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        Coffee coffee = coffeeService.saveCoffee(arr[0],
                                Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(arr[1])));
                        coffees.add(coffee);
                    }
                }
            } catch (IOException e) {
                log.error("exception: {}", e);
            }
        }
        return coffees;
    }

    @GetMapping(path = "/", params = "!name")
    public List<Coffee> getAllCoffee() {
        log.info("get all coffee");
        return coffeeService.getAllCoffee();
    }

    @GetMapping(path = "/", params = "name")
    public List<Coffee> getByName(@RequestParam("name") String name) {
        log.info("Get coffee by name: {}", name);
        List<Coffee> coffees = coffeeService.getCoffeeByName(name);
        return coffees;
    }

    @GetMapping(path = "/{id}")
    public Coffee getById(@PathVariable("id") Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        return coffee;
    }

    @GetMapping(path = "/delete/{id}")
    public boolean deleteById(@PathVariable("id") Long id) {
        return coffeeService.deleteCoffeeById(id);
    }
}
