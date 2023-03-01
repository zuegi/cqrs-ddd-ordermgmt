package ch.zuegi.ordermgmt.feature.food.domain;

import ch.zuegi.ordermgmt.feature.food.shared.Aggregate;
import ch.zuegi.ordermgmt.feature.food.shared.AggregateLifeCycle;
import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@ToString
@Aggregate
public class FoodCart {

    // FIXME AggregateIdentifier
    // Aktuell gehen wir davon aus, das an dieser Stelle immer eine UUID als AggregateIdentifier definiert ist
    // was natuerlich spaeter
//    @AggregateIdentfier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;
    private boolean confirmed;

    @CommandHandler
    public void handle(CreateFoodCartCommand command) {
        log.info("Ich bin ein {}",  command.getClass().getSimpleName());
         // create an event
        AggregateLifeCycle.apply(new FoodCartCreatedEvent(command.aggregateId()));

    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        log.info("Ich bin ein {}",  command.getClass().getSimpleName());
//        AggregateLifeCycle.apply(new ProductSelectedEvent(foodCartId, command.productId(), command.quantity()));
    }


    // der EventHandler wird dann verwendet um den State des Aggregates zu erstellen
    // was dann über das repository anstelle Ticket.aggregateEvents(List<DomainEvents)
    // siehe FoodCartRepository
    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("Ich bin ein FoodCartCreatedEvent: {}", event.toString());
        foodCartId = event.foodCartId();
        selectedProducts = new HashMap<>();
        confirmed = false;
    }


}
