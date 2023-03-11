package ch.zuegi.ordermgmt.feature.food.domain.command;

import ch.zuegi.ordermgmt.shared.annotation.TargetAggregateIdentifier;

import java.util.UUID;

public record ConfirmFoodCartCommand(@TargetAggregateIdentifier UUID foodCartId) {
}
