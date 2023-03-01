package ch.zuegi.ordermgmt.feature.food.domain.command;

import java.util.UUID;

public record ProductSelectedEvent(UUID foodCartId, UUID uuid, int quantity) {
}
