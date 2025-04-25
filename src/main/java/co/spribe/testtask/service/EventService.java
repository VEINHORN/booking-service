package co.spribe.testtask.service;

import co.spribe.testtask.model.entity.Booking;
import co.spribe.testtask.model.entity.Event;
import co.spribe.testtask.model.entity.EventType;
import co.spribe.testtask.model.entity.User;
import co.spribe.testtask.repository.EventRepository;
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
