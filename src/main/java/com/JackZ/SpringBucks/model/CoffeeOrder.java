package com.JackZ.SpringBucks.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "t_order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CoffeeOrder extends BaseEntity implements Serializable {

    private String customer;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "t_coffee_order")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(name = "state", nullable = false)
    private OrderState status;
}
