package ch.zuegi.ordermgmt.feature.ticket.domain.command;

import ch.zuegi.ordermgmt.shared.annotation.TargetAggregateIdentifier;

import java.util.UUID;

public record CreateTicketCommand(@TargetAggregateIdentifier UUID ticketId) {
}
