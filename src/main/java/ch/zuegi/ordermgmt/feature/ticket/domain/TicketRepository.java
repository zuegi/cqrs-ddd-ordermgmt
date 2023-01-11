package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.DomainEvent;

import java.util.Optional;

public interface TicketRepository {
    Optional<Ticket> findByTicketId(TicketId ticketId);

    void save(DomainEvent<?, TicketId> domainEvent);

}
