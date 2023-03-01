package ch.zuegi.ordermgmt.feature.food.domain.event;

import java.util.UUID;

public record FoodCartCreatedEvent(UUID foodCartId) {

}
