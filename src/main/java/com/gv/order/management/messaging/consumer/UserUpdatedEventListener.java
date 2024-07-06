package com.gv.order.management.messaging.consumer;

import com.gv.order.management.messaging.event.UserUpdatedEvent;
import com.gv.order.management.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
@Configuration
public class UserUpdatedEventListener {
    private final OrderService orderService;

    @Bean
    public Consumer<UserUpdatedEvent> userUpdatedEventConsumer() {
        return event -> {
            log.info("Received [UserUpdatedEvent] event: " + event);
            orderService.handleUserUpdatedEvent(new UserUpdatedEvent(1L, UserUpdatedEvent.EventType.DELETE));
        };
    }
}
