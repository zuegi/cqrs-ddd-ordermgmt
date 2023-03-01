package ch.zuegi.ordermgmt.feature.food.domain.command;

import java.util.UUID;

public record SelectProductCommand(
//        @TargetAggregateIdentifier
        UUID foodCartId,
        UUID productId,
        int quantity
) {
}
