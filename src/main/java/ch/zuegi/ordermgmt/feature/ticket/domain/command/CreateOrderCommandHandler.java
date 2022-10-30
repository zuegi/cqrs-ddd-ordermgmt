package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.shared.CommandHandler;

public class CreateOrderCommandHandler implements CommandHandler<CreateOrder, OrderEntity, OrderId> {

    @Override
    public OrderEntity handle(OrderId aggregateId, CreateOrder createOrder) {
        return null;
    }
}
