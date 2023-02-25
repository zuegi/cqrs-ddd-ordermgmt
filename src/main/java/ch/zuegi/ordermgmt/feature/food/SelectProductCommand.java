package ch.zuegi.ordermgmt.feature.food;

import java.util.UUID;

public record SelectProductCommand(
//        @TargetAggregateIdentifier
        UUID foodCartId,
        UUID productId,
        int quantity
) {
}
