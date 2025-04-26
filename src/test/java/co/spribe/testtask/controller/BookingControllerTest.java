package co.spribe.testtask.controller;

import co.spribe.testtask.model.request.BookingRequest;
import co.spribe.testtask.model.response.BookingResponse;
import co.spribe.testtask.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void testCreatingBooking() throws Exception {
        var request = new BookingRequest(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), LocalDate.now());

        var booking = new BookingResponse(UUID.randomUUID());
        when(bookingService.createBooking(any())).thenReturn(booking);

        mockMvc
                .perform(post("/bookings").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(booking.id().toString()));
    }

    @Test
    void testCancellingOfBooking() throws Exception {
        mockMvc
                .perform(delete("/bookings/{id}", UUID.randomUUID()))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).cancelBooking(any());
    }
}