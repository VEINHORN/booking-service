package co.spribe.testtask.controller;

import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public void createBooking(@RequestBody BookingRequest request) {
        bookingService.createBooking(request);
    }
}
