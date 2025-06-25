package com.veinhorn.booking.service.listener;

import com.veinhorn.booking.service.model.entity.AccomodationType;
import com.veinhorn.booking.service.model.entity.User;
import com.veinhorn.booking.service.model.request.UnitRequest;
import com.veinhorn.booking.service.repository.UserRepository;
import com.veinhorn.booking.service.service.UnitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppStartUpListener {
    private final UserRepository userRepository;
    private final UnitService unitService;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        for (int i = 0; i < 90; i++) {
            unitService.createUnit(createUnitRequest());
        }
    }

    private UnitRequest createUnitRequest() {
        return new UnitRequest(
                getUser().getId(),
                ThreadLocalRandom.current().nextInt(1, 5),
                AccomodationType.values()[ThreadLocalRandom.current().nextInt(AccomodationType.values().length)],
                ThreadLocalRandom.current().nextInt(1, 5),
                BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(10, 100)),
                "Unit %d".formatted(ThreadLocalRandom.current().nextInt(1, 100))
        );
    }

    private User getUser() {
        var users = userRepository.findAll();

        return users.isEmpty() ? userRepository.save(new User(null, "admin")) : users.getFirst();
    }
}
