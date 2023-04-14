package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveFoodCartCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveFoodCartHandlerImpl;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.FoodCartId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;

public class FoodCart extends AggregateRoot<FoodCart, FoodCartId> {
    public FoodCart(FoodCartId aggregateID) {
        super(aggregateID);
    }

    @Override
    public FoodCartId id() {
        return this.aggregateId;
    }

    @Override
    protected AggregateRootBehavior<FoodCartId> initialBehavior() {
        AggregateRootBehaviorBuilder<FoodCartId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(SaveFoodCartCommand.class, new SaveFoodCartHandlerImpl());
        return behaviorBuilder.build();
    }
}
