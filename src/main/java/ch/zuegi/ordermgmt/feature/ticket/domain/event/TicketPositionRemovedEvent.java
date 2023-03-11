package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.shared.annotation.AggregatedEventIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketPositionRemovedEvent(

        @AggregatedEventIdentifier
        UUID ticketId,
        UUID ticketPositionId,
        UUID tradeItemId,
        BigDecimal menge
) {
}
