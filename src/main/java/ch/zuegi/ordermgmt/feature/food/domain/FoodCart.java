package ch.zuegi.ordermgmt.feature.food.domain;

import ch.zuegi.ordermgmt.feature.food.domain.command.ConfirmFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.CreateFoodCartCommand;
import ch.zuegi.ordermgmt.feature.food.domain.command.SelectProductCommand;
import ch.zuegi.ordermgmt.feature.food.domain.event.ConfirmedFoodCartEvent;
import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.feature.food.domain.event.ProductSelectedEvent;
import ch.zuegi.ordermgmt.shared.annotation.Aggregate;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.EventSourceHandler;
import ch.zuegi.ordermgmt.shared.gateway.command.AggregateLifeCycle;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@ToString
@Getter
@Aggregate
public class FoodCart {

    // FIXME AggregateIdentifier
    // Aktuell gehen wir davon aus, das an dieser Stelle immer eine UUID als AggregateIdentifier definiert ist
    // was natuerlich spaeter
//    @AggregateIdentfier
    private UUID foodCartId;
    private Map<UUID, Integer> selectedProducts;
    private boolean confirmed;

    /**********************
     * CommandHandler     *
     *********************/
    @CommandHandler
    public void handle(CreateFoodCartCommand command) {
        log.info("Ich bin ein {}: {}",  command.getClass().getSimpleName(), command);
         // create an event
        AggregateLifeCycle.apply(new FoodCartCreatedEvent(command.uuid()));

    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        log.info("Ich bin ein {}: {}",  command.getClass().getSimpleName(), command);
        AggregateLifeCycle.apply(new ProductSelectedEvent(command.foodCartId(), command.productId(), command.quantity()));
    }

    @CommandHandler
    public void handle(ConfirmFoodCartCommand command) {
        log.info("Ich bin ein {}: {}",  command.getClass().getSimpleName(), command);
        AggregateLifeCycle.apply(new ConfirmedFoodCartEvent(command.foodCartId()));
    }

    /**********************
     * EventSourceHandler *
     *********************/

    // der EventHandler wird dann verwendet um den State des Aggregates zu erstellen
    @EventSourceHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("Ich bin ein {}: {}", event.getClass().getSimpleName(), event);
        foodCartId = event.foodCartId();
        selectedProducts = new HashMap<>();
        confirmed = false;
    }

    @EventSourceHandler
    public void on(ProductSelectedEvent event) {
        log.info("Ich bin ein {}: {}", event.getClass().getSimpleName(), event);
        selectedProducts.merge(event.productId(), event.quantity(), Integer::sum);
    }
    @EventSourceHandler
    public void on(ConfirmedFoodCartEvent event) {
        log.info("Ich bin ein {}: {}", event.getClass().getSimpleName(), event);
        this.confirmed = true;
    }


}
