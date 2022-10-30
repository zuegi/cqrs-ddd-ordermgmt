package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrder;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.shared.Entity;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehaviorBuilder;

import java.util.ArrayList;
import java.util.List;

public class Order extends AggregateRoot<Order, OrderId> {

    Entity<OrderId> orderEntity;
    List<OrderPosition> orderPositionList;

    private Order(OrderId aggregateID) {
        super(aggregateID);
    }


    public static Order create(OrderId processingOrderId) {
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

    public void addOrderPosition(OrderPosition orderPosition) {
        if (this.orderPositionList == null) {
            this.orderPositionList = new ArrayList<>();
        }
        this.orderPositionList.add(orderPosition);

    }

    public void addEntity(Entity<OrderId> entity) {
        this.orderEntity = entity;
    }
}
