package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.shared.annotation.AggregatedEventIdentifier;

import java.util.UUID;

public record TicketConfirmedEvent(@AggregatedEventIdentifier UUID ticketId) {

}



