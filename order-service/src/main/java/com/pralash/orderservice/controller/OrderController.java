package com.pralash.orderservice.controller;

import com.pralash.orderservice.dto.OrderRequest;
import com.pralash.orderservice.model.Order;
import com.pralash.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String placeOrder(@RequestBody @Valid OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order has been placed Successfully";
    }
}
