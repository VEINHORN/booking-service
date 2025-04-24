package co.spribe.testtask.controller;

import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.model.response.BookingResponse;
import co.spribe.testtask.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @DeleteMapping("/{id}")
    public void cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
    }
}
