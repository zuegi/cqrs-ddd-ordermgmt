package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderTicketId;
import ch.zuegi.ordermgmt.shared.Entity;

public class OrderTicketEntity implements Entity<OrderTicketEntity, OrderTicketId> {

    @Override
    public boolean sameIdentityAs(OrderTicketEntity other) {
        // TODO Implementierung
        return false;
    }

    @Override
    public OrderTicketId id() {
        // TODO Implementierung
        return null;
    }
}
