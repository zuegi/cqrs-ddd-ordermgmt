package ch.zuegi.ordermgmt.feature.food;

import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;

import java.util.UUID;

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
        System.out.println("Ich bin ein " +command.getClass().getSimpleName());
        // handle command and create an event
        UUID aggregateId = UUID.randomUUID();
//        AggregateLifeCycle.apply(new FoodCartCreatedEvent(aggregateId));

    }



//    @EventSourceHandler // ist die Ersetzung des TicketDomainHandler
    public void on(FoodCartCreatedEvent event) {
        //
        foodCartId = event.foodCartId();
    }


}
