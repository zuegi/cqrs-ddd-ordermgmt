package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrder;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;

public class Order extends AggregateRoot<Order, OrderId> {

    private Order(OrderId aggregateID) {
        super(aggregateID);
    }


    public static Order create(OrderId processingOrderId, CreateOrder createCustomerOrder) {
        Order processingOrder = new Order(processingOrderId);
        processingOrder.handleCommand(createCustomerOrder);
        return processingOrder;
    }


    @Override
    public OrderId id() {
        return null;
    }

    @Override
    protected AggregateRootBehavior<OrderId> initialBehavior() {
        return null;
    }
}
