package com.veinhorn.booking.service.controller;

import com.veinhorn.booking.service.model.entity.AccomodationType;
import com.veinhorn.booking.service.model.request.UnitRequest;
import com.veinhorn.booking.service.model.response.AvailableUnitResponse;
import com.veinhorn.booking.service.service.StatsService;
import com.veinhorn.booking.service.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnitController.class)
class UnitControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private UnitService unitService;

    @MockitoBean
    private StatsService statsService;

    @Test
    void testGettingAvailableUnitsStats() throws Exception {
        var response = new AvailableUnitResponse(new AtomicLong(1));
        when(statsService.getAvailableUnits()).thenReturn(response);

        mockMvc.perform(get("/units/available")).andExpect(status().isOk());

        verify(statsService, times(1)).getAvailableUnits();
    }

    @Test
    void testCreatingUnit() throws Exception {
        var request = new UnitRequest(UUID.randomUUID(), 1, AccomodationType.FLAT, 2, BigDecimal.valueOf(10), "Flat 1");

        mockMvc
                .perform(post("/units").content(mapper.writeValueAsString(request)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(unitService, times(1)).createUnit(any());
    }
}