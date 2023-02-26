package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@Aggregate
public class FoodCart {


//    @AggregateIdentfier
    private UUID foodCartId;

    // diese Methode ist nur zu Testzwecken waehrend der Entwicklung
    public void testMethod() {
        System.out.println("Hallo ich bin "+this.getClass().getSimpleName());
    }

    @CommandHandler
    public void handle(CreateFoodCartCommand command) {
        log.info("Ich bin ein {}",  command.getClass().getSimpleName());
        // handle command
         UUID aggregateId = UUID.randomUUID();

         // create an event
        AggregateLifeCycle.apply(new FoodCartCreatedEvent(aggregateId));

    }

    @CommandHandler
    public void handle(SelectProductCommand command) {
        log.info("Ich bin ein {}",  command.getClass().getSimpleName());
//        AggregateLifecycle.apply(new ProductSelectedEvent(foodCartId, command.productId(), command.quantity()));
    }


    @EventHandler
    public void on(FoodCartCreatedEvent event) {

        log.info("Ich bin ein FoodCartCreatedEvent: {}", event.toString());
        foodCartId = event.foodCartId();
    }


}
