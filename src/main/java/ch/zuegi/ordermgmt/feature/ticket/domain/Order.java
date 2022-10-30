package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrder;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehaviorBuilder;

public class Order extends AggregateRoot<Order, OrderId> {

    private Order(OrderId aggregateID) {
        super(aggregateID);
    }


    public static Order create(OrderId processingOrderId, CreateOrder createCustomerOrder) {
        return new Order(processingOrderId);
    }


    @Override
    public OrderId id() {
        return aggregateId;
    }

    @Override
    protected AggregateRootBehavior<OrderId> initialBehavior() {
        AggregateRootBehaviorBuilder<OrderId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(CreateOrder.class, new CreateOrderCommandHandler());
        return behaviorBuilder.build();
    }
}
