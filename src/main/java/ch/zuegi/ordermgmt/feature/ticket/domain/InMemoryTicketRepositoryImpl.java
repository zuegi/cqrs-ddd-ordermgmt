package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionRemovedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryTicketRepositoryImpl implements TicketRepository {

    List<DomainEvent<?, TicketId>> domainEventList = new ArrayList<>();

    @Override
    public Optional<Ticket> findByTicketId(TicketId ticketId) {

        Optional<DomainEvent<?, TicketId>> optionalTicketCreatedEvent = extractTicketCreatedEvent(ticketId);

        if (optionalTicketCreatedEvent.isPresent()) {
            List<DomainEvent<?, TicketId>> ticketDomainEvents = domainEventList.stream()
                    .filter(event -> event.id().sameValueAs(ticketId))
                    .toList();
            Ticket ticket = new Ticket(ticketId);
            ticket.aggregateEvents(ticketDomainEvents);
            return Optional.of(ticket);
        }

        return Optional.empty();
    }

    private Optional<DomainEvent<?, TicketId>> extractTicketCreatedEvent(TicketId ticketId) {
        return domainEventList.stream()
                .filter(event -> event instanceof TicketCreatedEvent)
                .filter(event -> ((TicketCreatedEvent) event).getTicketId().equals(ticketId))
                .findAny();
    }

    // TODO only one save method must remain
    // save(DomainEvent<>)

    @Override
    public void save(TicketCreatedEvent ticketCreatedEvent) {
        domainEventList.add(ticketCreatedEvent);
    }

    @Override
    public void save(TicketPositionAddedEvent ticketPositionAddedEvent) {
        domainEventList.add(ticketPositionAddedEvent);
    }

    @Override
    public void save(TicketPositionRemovedEvent ticketPositionRemovedEvent) {
        domainEventList.add(ticketPositionRemovedEvent);
    }
}
