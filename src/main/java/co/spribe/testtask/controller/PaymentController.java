package co.spribe.testtask.controller;

import co.spribe.testtask.service.PaymentService;
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

    // Web Hook endpoint for simulating accepting payments
    @PostMapping("/{id}")
    public void acceptPayment(@PathVariable UUID id) {
        paymentService.acceptPayment(id);
    }
}
