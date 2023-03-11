package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.shared.annotation.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

public record RemoveTicketPositionCommand(
        @TargetAggregateIdentifier
        UUID ticketId,
        UUID ticketPositionId,
        UUID tradeItemId,
        BigDecimal menge
) {
}
