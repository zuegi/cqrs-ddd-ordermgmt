package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketPosition;

import java.util.stream.Collectors;

public class TicketEventBuilder {



    public static TicketCreated build(Ticket ticket, TicketCreated.TicketCreatedBuilder createdBuilder) {

        return createdBuilder
                .ticketNumber(ticket.id())
                .lifeCycleState(ticket.getTicketLifeCycleState())
                .localDateTime(ticket.getLocalDateTime())
                .ticketPositionNumberSet(ticket.getTicketPositionSet().stream().map(TicketPosition::id).collect(Collectors.toSet()))
                .build();

    }

    public static TicketLifecycleUpdated build(Ticket ticket, TicketLifecycleUpdated.TicketLifecycleUpdatedBuilder lifecycleUpdatedBuilder) {
       return  lifecycleUpdatedBuilder
               .ticketNumber(ticket.id())
               .localDateTime(ticket.getLocalDateTime())
               .currentLifeCycleState(ticket.getTicketLifeCycleState())
               // TODO get?
               .previousLifeCycleState(ticket.getTicketLifeCycleState().prev().get())
               .build();

    }
}
