package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class TicketPositionCreatedLogger implements DomainEventSubscriber {

    @Override
    public void handle(DomainEvent<?> domainEvent) {
        log.info("DomainEvent: {}", domainEvent.getEvent().toString());
    }

    @Override
    public Class<TicketPositionCreatedLogger> supports() {
        return TicketPositionCreatedLogger.class;
    }
}
