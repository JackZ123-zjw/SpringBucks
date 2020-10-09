package com.JackZ.SpringBucks.Controller.request;

import com.JackZ.SpringBucks.model.OrderState;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class UpdateOrderStatusRequest {

    @NotNull
    private Long id;
    @NotEmpty
    private OrderState status;
}
