package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.shared.annotation.TargetAggregateIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateTicketLifecycleCommand(
        @TargetAggregateIdentifier
        UUID ticketId,
        TicketLifeCycleState ticketLifeCycleState
) {
}
