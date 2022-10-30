package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Entity;

public class TicketEntity extends Entity<TicketId> {

    public TicketEntity(TicketId aggregateId) {
        super(aggregateId);
    }

    @Override
    public TicketId id() {
        return this.aggregateId;
    }
}
