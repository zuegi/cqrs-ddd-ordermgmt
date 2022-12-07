package ch.zuegi.ordermgmt.feature.ticket.domain.event.logger;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreatedEvent;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class TicketPositionCreatedLogger implements DomainEventSubscriber {

    @Override
    public void handle(DomainEvent<?> domainEvent) {
        TicketPositionCreatedEvent event = (TicketPositionCreatedEvent) domainEvent.getEvent();
        log.info("DomainEvent: {}", event.getTicketPositionId());
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(TicketPositionCreatedEvent.class);
    }
}
