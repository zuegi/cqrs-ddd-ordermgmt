package ch.zuegi.ordermgmt.feature.ticket.domain.event.logger;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class TicketCreatedLogger implements DomainEventSubscriber {


    @Override
    public void handle(DomainEvent<?> domainEvent) {
        TicketCreatedEvent ticketCreatedEvent = (TicketCreatedEvent) domainEvent.getEvent();
        log.info("TicketCreatedEvent on: {} with ticketId: {} and lifecycle state: {} and {} ticket positions",
                ticketCreatedEvent.getLocalDateTime(), ticketCreatedEvent.getTicketId().id,
                ticketCreatedEvent.getLifeCycleState(), ticketCreatedEvent.getTicketPositionNumberSet().size());
    }

    @Override
    public Set<Class<?>> supports() {
        return Set.of(TicketCreatedEvent.class);
    }
}
