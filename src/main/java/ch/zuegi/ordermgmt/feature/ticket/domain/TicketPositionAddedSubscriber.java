package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreated;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketPositionAddedSubscriber implements DomainEventSubscriber<TicketPositionCreated> {
    @Override
    public void handleEvent(TicketPositionCreated aDomainEvent) {
        log.info("TicketPositionAdded Event: {}", aDomainEvent);
    }

    @Override
    public Class<TicketPositionCreated> subscribedToEventType() {
        return null;
    }
}
