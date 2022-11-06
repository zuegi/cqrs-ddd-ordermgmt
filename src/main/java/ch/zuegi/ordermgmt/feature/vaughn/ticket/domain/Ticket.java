package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.Entity;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Ticket extends Entity<TicketId> {

    Set<TicketPosition> ticketPositionSet;

    public Ticket(TicketId aggregateId) {
        super(aggregateId);
        this.ticketPositionSet = new HashSet<>();

        TicketCreated ticketCreated = TicketCreated.eventOf(aggregateId, LocalDateTime.now());
        DomainEventPublisher
                .instance()
                .publish(ticketCreated);
    }

    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    public Ticket addTicketPosition(TicketPosition ticketPosition) {
        // FIXME validieren
        ticketPositionSet.add(ticketPosition);

        //add TicketPositionAdded in DomainPublisher
        TicketPositionAdded ticketPositionAdded = TicketPositionAdded.eventOf(ticketPosition.id(), ticketPosition.getTicketId(), ticketPosition.getTradeItemId(), ticketPosition.getMenge());
        DomainEventPublisher
                .instance()
                .publish(ticketPositionAdded);
        return this;
    }
}
