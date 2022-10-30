package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPositionCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.shared.Entity;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehaviorBuilder;
import lombok.Getter;

@Getter
public class OrderPosition extends AggregateRoot<OrderPosition, OrderPositionId> {


    public Entity<OrderPositionId> orderPositionEntity;

    private OrderPosition(OrderPositionId aggregateID) {
        super(aggregateID);
    }


    public static OrderPosition create(OrderPositionId orderPositionId) {
        return new OrderPosition(orderPositionId);
    }




    @Override
    public OrderPositionId id() {
        return this.aggregateId;
    }

    @Override
    protected AggregateRootBehavior<OrderPositionId> initialBehavior() {
        AggregateRootBehaviorBuilder<OrderPositionId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(CreateOrderPosition.class, new CreateOrderPositionCommandHandler());
        return behaviorBuilder.build();
    }

    public void addEntity(Entity<OrderPositionId> entity) {
        this.orderPositionEntity =  entity;
    }
}
