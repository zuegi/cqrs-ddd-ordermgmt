package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketSubscriber implements DomainEventSubscriber<TicketPositionAdded> {

    @Override
    public void handleEvent(TicketPositionAdded aDomainEvent) {
        log.info("ticketposition: {}", aDomainEvent);
    }

    @Override
    public Class subscribedToEventType() {
        return null;
    }
}
