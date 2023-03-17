package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.feature.food.domain.query.FindFoodCartQuery;
import ch.zuegi.ordermgmt.shared.annotation.EventHandler;
import ch.zuegi.ordermgmt.shared.annotation.QueryHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;


@Slf4j
@Component
public class FoodCartProjector {
    private final FoodCartViewRepository repository;

    public FoodCartProjector(FoodCartViewRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(FoodCartCreatedEvent event) {
        log.info("Ich bin ein {}: {}", event.getClass().getSimpleName(), event);

        FoodCartView foodCartView = new FoodCartView(event.foodCartId(), Collections.emptyMap());
        repository.save(foodCartView);

    }

    @QueryHandler
    public FoodCartView handle(FindFoodCartQuery foodCartQuery) {
        log.info("Ich bin eine {}: {}",  foodCartQuery.getClass().getSimpleName(), foodCartQuery);
        return repository.findById(foodCartQuery.foodCartId()).orElse(null);
    }
}
