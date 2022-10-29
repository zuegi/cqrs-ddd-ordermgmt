package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Entity;

public class TicketEntity implements Entity<TicketEntity, TicketId> {

    @Override
    public boolean sameIdentityAs(TicketEntity other) {
        // TODO Implementierung
        return false;
    }

    @Override
    public TicketId id() {
        // TODO Implementierung
        return null;
    }
}
