package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FoodCartRepository {

    /*
        Diese Klasse ist einfach mal zum testen ob der EventBus iva AggregateLifeCycle funktioniert
        und kann spaeter entfernt oder umgebaut werden
     */
    @EventHandler
    public void on(FoodCartCreatedEvent foodCartCreatedEvent) {
        log.info("Hello and welcome {}", foodCartCreatedEvent.foodCartId());
    }
}
