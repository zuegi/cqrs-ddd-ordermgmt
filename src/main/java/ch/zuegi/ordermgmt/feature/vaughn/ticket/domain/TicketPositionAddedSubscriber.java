package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketPositionAddedSubscriber implements DomainEventSubscriber<TicketPositionAdded> {
    @Override
    public void handleEvent(TicketPositionAdded aDomainEvent) {
        log.info("TicketPositionAdded Event: {}", aDomainEvent);
    }

    @Override
    public Class<TicketPositionAdded> subscribedToEventType() {
        return null;
    }
}
