package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderPositionCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.OrderPositionEntity;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderPositionId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehaviorBuilder;
import lombok.Getter;

@Getter
public class OrderPosition extends AggregateRoot<OrderPosition, OrderPositionId> {


    public static OrderPositionEntity orderPositionEntity;

    private OrderPosition(OrderPositionId aggregateID) {
        super(aggregateID);
    }

    @Override
    public OrderPositionId id() {
        return this.id();
    }


    public static OrderPosition create(OrderPositionId orderPositionId) {
        return new OrderPosition(orderPositionId);
    }


    @Override
    protected AggregateRootBehavior<OrderPositionId> initialBehavior() {
        AggregateRootBehaviorBuilder<OrderPositionId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        // FIXME Implementierung
        behaviorBuilder.setCommandHandler(CreateOrderPosition.class, new CreateOrderPositionCommandHandler());
        return behaviorBuilder.build();
    }
}
