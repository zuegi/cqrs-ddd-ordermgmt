package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

public record TicketPosition(
        UUID ticketPositionId,
        UUID tradeItemId,
        BigDecimal menge
) {
}
