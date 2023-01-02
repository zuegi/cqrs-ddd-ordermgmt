package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InMemoryTicketRepositoryImpl implements TicketRepository {

    List<TicketCreatedEvent> ticketCreatedEventList = new ArrayList<>();
    List<TicketPositionAddedEvent> ticketPositionAddedEventList = new ArrayList<>();

    @Override
    public Optional<Ticket> findByTicketId(TicketId ticketId) {
        Optional<TicketCreatedEvent> ticketCreatedEvent = ticketCreatedEventList.stream()
                .filter(event -> event.getTicketId().equals(ticketId))
                .findAny();


        if (ticketCreatedEvent.isPresent()) {
            Ticket ticket = new Ticket(ticketId);
            ticket.aggregateEvent(ticketCreatedEvent.get());

            List<TicketPositionAddedEvent> ticketPositionAddedEvents = ticketPositionAddedEventList.stream()
                    .filter(event -> event.getTicketId().equals(ticketId))
                    .toList();
            if (!ticketPositionAddedEvents.isEmpty()) {
                ticket.aggregateTicketPositionEvents(ticketPositionAddedEvents);
            }
            return Optional.of(ticket);
        }
        return Optional.empty();
    }


    @Override
    public void save(TicketCreatedEvent ticketCreatedEvent) {
        ticketCreatedEventList.add(ticketCreatedEvent);
    }

    @Override
    public void save(TicketPositionAddedEvent ticketPositionAddedEvent) {
        ticketPositionAddedEventList.add(ticketPositionAddedEvent);
    }
}
