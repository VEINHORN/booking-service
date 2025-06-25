package com.veinhorn.booking.service.service;

import com.veinhorn.booking.service.model.entity.Booking;
import com.veinhorn.booking.service.model.entity.Event;
import com.veinhorn.booking.service.model.entity.EventType;
import com.veinhorn.booking.service.model.entity.User;
import com.veinhorn.booking.service.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public void storeEvent(EventType eventType, Booking booking, User user) {
        var event = new Event();
        event.setEventType(eventType);
        event.setBooking(booking);
        event.setUser(user);
        event.setCreatedAt(LocalDateTime.now());

        eventRepository.save(event);
    }
}
