package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddEvent;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TicketPositionAddedSubscriber implements DomainEventSubscriber<TicketPositionAddEvent> {
    @Override
    public void handleEvent(TicketPositionAddEvent aDomainEvent) {
        log.info("TicketPositionAdded Event: {}", aDomainEvent);
    }

    @Override
    public Class<TicketPositionAddEvent> subscribedToEventType() {
        return null;
    }
}
