package ch.zuegi.ordermgmt.feature.food.infrastructure.persistence;

import ch.zuegi.ordermgmt.feature.food.domain.FoodCart;
import ch.zuegi.ordermgmt.feature.food.domain.event.ProductSelectedEvent;
import ch.zuegi.ordermgmt.feature.food.domain.event.FoodCartCreatedEvent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

class EventRepositoryUnitTest {


    @Test
    void on() {
        UUID foodCartCreatedEventId = UUID.randomUUID();
        UUID productSelectedEventID = UUID.randomUUID();

        EventRepository eventRepository = new EventRepository();
        var foodCartEvent = new FoodCartCreatedEvent(foodCartCreatedEventId);
        var productSelectedEvent = new ProductSelectedEvent(foodCartCreatedEventId, productSelectedEventID, 2);
        eventRepository.on(foodCartEvent);
        eventRepository.on(productSelectedEvent);

        Optional<Object> byTargetIdentifier = eventRepository.findByTargetIdentifier(foodCartCreatedEventId);
        Assertions.assertThat(byTargetIdentifier.isPresent()).isTrue();
        FoodCart foodCart = (FoodCart) byTargetIdentifier.get();
        Assertions.assertThat(foodCart)
                .isNotNull()
                .extracting(FoodCart::getFoodCartId, FoodCart::isConfirmed)
                .contains(foodCartCreatedEventId,false);

        Assertions.assertThat(foodCart.getSelectedProducts())
                .containsEntry(productSelectedEventID, 2);

    }

    @Test
    void testemich() {

        Map<UUID, List<String>> map = new HashMap<>();

        UUID key = UUID.randomUUID();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Ich bin ein String");
        strings.add("Ich bin auch ein String");
        map.put(key, strings);

        map.entrySet().stream().map(me -> {
                    return "Key: " + me.getKey() + "\n"
                            + "Values: " + String.join(", ", me.getValue());
                })
                .forEach(System.out::print);



    }

}
