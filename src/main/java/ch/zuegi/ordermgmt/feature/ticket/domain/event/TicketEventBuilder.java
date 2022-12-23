package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketPosition;

import java.util.stream.Collectors;

public class TicketEventBuilder {



    public static TicketCreatedEvent build(Ticket ticket, TicketCreatedEvent.TicketCreatedEventBuilder createdBuilder) {

        return createdBuilder
                .ticketId(ticket.id())
                .lifeCycleState(ticket.getTicketLifeCycleState())
                .localDateTime(ticket.getLocalDateTime())
//                .ticketPositionNumberSet(ticket.getTicketPositionSet().stream().map(TicketPosition::id).collect(Collectors.toSet()))
                .build();

    }

    public static TicketLifecycleUpdatedEvent build(Ticket ticket, TicketLifecycleUpdatedEvent.TicketLifecycleUpdatedEventBuilder lifecycleUpdatedBuilder) {
       return  lifecycleUpdatedBuilder
               .ticketNumber(ticket.id())
               .localDateTime(ticket.getLocalDateTime())
               .currentLifeCycleState(ticket.getTicketLifeCycleState())
               // TODO get?
               .previousLifeCycleState(ticket.getTicketLifeCycleState().prev().get())
               .build();

    }

    public static TicketPositionCreatedEvent build(TicketPosition ticketPosition, TicketPositionCreatedEvent.TicketPositionCreatedEventBuilder builder) {
        return builder
                .ticketPositionId(ticketPosition.id())
                .ticketId(ticketPosition.getTicketId())
                .tradeItemId(ticketPosition.getTradeItemId())
                .menge(ticketPosition.getMenge())
                .build();
    }
}
