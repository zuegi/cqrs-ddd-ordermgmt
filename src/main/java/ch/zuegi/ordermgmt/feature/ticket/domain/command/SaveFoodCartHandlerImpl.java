package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.FoodCart;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.FoodCartSaveEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.FoodCartId;
import ch.zuegi.ordermgmt.shared.CommandHandler;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;

public class SaveFoodCartHandlerImpl implements CommandHandler<SaveFoodCartCommand, FoodCartSaveEvent, FoodCartId> {

    @Override
    public FoodCartSaveEvent handle(FoodCartId aggregateId, SaveFoodCartCommand command) {

        FoodCartSaveEvent foodCartSaveEvent = FoodCartSaveEvent.eventOf(aggregateId);
        DomainEventPublisher
                .instance()
                .publish(foodCartSaveEvent);

        return foodCartSaveEvent;
    }
}
