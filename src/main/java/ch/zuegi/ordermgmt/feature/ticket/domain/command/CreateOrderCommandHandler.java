package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateOrderCommandHandler implements CommandHandler<CreateOrder, OrderEntity, OrderId> {

    @Override
    public OrderEntity handle(OrderId aggregateId, CreateOrder createOrder) {
        log.info("{} called with {}", this.getClass().getName(), createOrder);

        OrderEntity orderEntity = new OrderEntity(aggregateId);

        return orderEntity;
    }
}
