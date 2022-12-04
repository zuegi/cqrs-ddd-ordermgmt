package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.shared.DomainEvent;
import ch.zuegi.ordermgmt.shared.DomainEventSubscriber;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;


@Slf4j
public class TicketCreatedLogger implements DomainEventSubscriber {


    @Override
    public void handle(DomainEvent<?> domainEvent) {
        log.info("DomainEvent: {} as event {}", domainEvent.getClass(), domainEvent.getEvent().toString());
    }

    @Override
    public Class<TicketCreatedLogger> supports() {
        return TicketCreatedLogger.class;
    }
}
