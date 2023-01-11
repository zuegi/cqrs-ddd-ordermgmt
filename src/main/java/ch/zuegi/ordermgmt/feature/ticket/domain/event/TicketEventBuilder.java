package ch.zuegi.ordermgmt.feature.ticket.domain.event;

import ch.zuegi.ordermgmt.feature.ticket.domain.Ticket;
import ch.zuegi.ordermgmt.feature.ticket.domain.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.Command;
import ch.zuegi.ordermgmt.shared.DomainEvent;

public class TicketEventBuilder {


    @SuppressWarnings("unchecked")
    public static <T> DomainEvent<T, TicketId> build(Ticket ticket, Command command) {
        if (command instanceof CreateTicketCommand) {
            return (DomainEvent<T, TicketId>) build(ticket, TicketCreatedEvent.builder());
        }
        // and so on
        return null;
    }

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
               .ticketId(ticket.id())
               .localDateTime(ticket.getLocalDateTime())
               .currentLifeCycleState(ticket.getTicketLifeCycleState())
               // TODO get?
               .previousLifeCycleState(ticket.getTicketLifeCycleState().prev().get())
               .build();

    }

    public static TicketPositionAddedEvent build(TicketPosition ticketPosition, TicketPositionAddedEvent.TicketPositionAddedEventBuilder builder) {
        return builder
                .ticketPositionId(ticketPosition.id())
                .ticketId(ticketPosition.getTicketId())
                .tradeItemId(ticketPosition.getTradeItemId())
                .menge(ticketPosition.getMenge())
                .build();
    }


    public static TicketPositionRemovedEvent build(TicketPosition ticketPosition, TicketPositionRemovedEvent.TicketPositionRemovedEventBuilder builder) {
        return builder
                .ticketPositionId(ticketPosition.id())
                .ticketId(ticketPosition.getTicketId())
                .tradeItemId(ticketPosition.getTradeItemId())
                .menge(ticketPosition.getMenge())
                .build();
    }

    public static TicketConfirmedEvent build(Ticket ticket, TicketConfirmedEvent.TicketConfirmedEventBuilder builder) {
        return builder
                .ticketId(ticket.id())
                .ticketLifeCycleState(ticket.getTicketLifeCycleState())
                .build();
    }
}
