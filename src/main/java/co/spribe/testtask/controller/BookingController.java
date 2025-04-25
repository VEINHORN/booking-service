package co.spribe.testtask.controller;

import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.model.response.BookingResponse;
import co.spribe.testtask.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Book a unit in date range")
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel specific booking")
    public void cancelBooking(@PathVariable UUID id) {
        bookingService.cancelBooking(id);
    }
}
