package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.domain.FoodCart;
import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import ch.zuegi.ordermgmt.feature.food.domain.event.ProductSelectedEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class EventRepositoryIntegrationTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    void on() {
        // given
        UUID foodCartCreatedEventId = UUID.randomUUID();
        UUID productSelectedEventID = UUID.randomUUID();


        var foodCartEvent = new FoodCartCreatedEvent(foodCartCreatedEventId);
        var productSelectedEvent = new ProductSelectedEvent(foodCartCreatedEventId, productSelectedEventID, 2);
        eventRepository.on(foodCartEvent);
        eventRepository.on(productSelectedEvent);

        // when
        Optional<Object> byTargetIdentifier = eventRepository.findByTargetIdentifier(foodCartCreatedEventId);
        Assertions.assertThat(byTargetIdentifier.isPresent()).isTrue();
        FoodCart foodCart = (FoodCart) byTargetIdentifier.get();

        // then
        Assertions.assertThat(foodCart)
                .isNotNull()
                .extracting(FoodCart::getFoodCartId, FoodCart::isConfirmed)
                .contains(foodCartCreatedEventId,false);

        Assertions.assertThat(foodCart.getSelectedProducts())
                .containsEntry(productSelectedEventID, 2);

    }

}
