package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.Entity;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;

import java.util.Set;

public class Ticket extends Entity<TicketId> {

    Set<TicketPosition> ticketPositionSet;

    public Ticket(TicketId aggregateId) {
        super(aggregateId);
    }

    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    public void plannedTicketPosition(TicketPosition ticketPosition) {
        // FIXME validieren
        ticketPositionSet.add(ticketPosition);

    }
}
