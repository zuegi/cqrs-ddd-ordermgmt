package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.shared.Entity;

public class OrderEntity extends Entity<OrderId> {


    public OrderEntity(OrderId aggregateId) {
        super(aggregateId);
    }

    @Override
    public OrderId id() {
        return aggregateId;
    }
}
