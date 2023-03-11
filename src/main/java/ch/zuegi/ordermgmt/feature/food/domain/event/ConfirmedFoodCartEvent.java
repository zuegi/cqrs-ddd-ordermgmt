package ch.zuegi.ordermgmt.feature.food.domain.event;

import ch.zuegi.ordermgmt.shared.annotation.AggregatedEventIdentifier;

import java.util.UUID;

public record ConfirmedFoodCartEvent(@AggregatedEventIdentifier UUID foodCardId) {
}
