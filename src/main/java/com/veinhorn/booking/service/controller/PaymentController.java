package com.veinhorn.booking.service.controller;

import com.veinhorn.booking.service.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/{id}")
    @Operation(summary = "Endpoint to simulate accepting payments")
    public void acceptPayment(@PathVariable UUID id) {
        paymentService.acceptPayment(id);
    }
}
