package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.shared.annotation.AggregatedEventIdentifier;

import java.time.LocalDateTime;
import java.util.UUID;


public record TicketLifecycleUpdatedEvent(
        @AggregatedEventIdentifier
        UUID ticketId,
        TicketLifeCycleState ticketLifeCycleState
) {
}
