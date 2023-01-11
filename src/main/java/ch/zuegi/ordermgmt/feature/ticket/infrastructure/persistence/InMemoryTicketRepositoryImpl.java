package ch.zuegi.ordermgmt.feature.ticket.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketRepository;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
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

    @Override
    public void save(DomainEvent<?, TicketId> domainEvent) {
        domainEventList.add(domainEvent);
    }

    private Optional<DomainEvent<?, TicketId>> extractTicketCreatedEvent(TicketId ticketId) {
        return domainEventList.stream()
                .filter(event -> event instanceof TicketCreatedEvent)
                .filter(event -> ((TicketCreatedEvent) event).getTicketId().equals(ticketId))
                .findAny();
    }

}
