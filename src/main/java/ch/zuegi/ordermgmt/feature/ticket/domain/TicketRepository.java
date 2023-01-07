package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionRemovedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findByTicketId(TicketId ticketId);

    void save(TicketCreatedEvent ticketCreatedEvent);

    void save(TicketPositionAddedEvent ticketPositionAddedEvent);

    void save(TicketPositionRemovedEvent ticketPositionRemovedEvent);
}
