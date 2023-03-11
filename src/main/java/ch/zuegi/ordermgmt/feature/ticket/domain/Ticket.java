package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.*;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.*;
import ch.zuegi.ordermgmt.shared.annotation.Aggregate;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.gateway.command.AggregateLifeCycle;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@ToString
@Aggregate
public class Ticket {

    private UUID ticketId;
    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private List<TicketPosition> ticketPositionList;

    @CommandHandler
    public void handle(UpdateTicketLifecycleCommand command) {
        AggregateLifeCycle.apply(
                new TicketLifecycleUpdatedEvent(command.ticketId(), command.ticketLifeCycleState())
        );

    }

    @CommandHandler
    public void handle(CreateTicketCommand command) {
        AggregateLifeCycle.apply(
                new TicketCreatedEvent(command.ticketId(), LocalDateTime.now(), TicketLifeCycleState.TICKET_CREATED));
    }

    @CommandHandler
    public void handle(AddTicketPositionCommand command) {
        AggregateLifeCycle.apply(
                new TicketPositionAddedEvent(command.ticketId(),command.ticketPositionId(), command.tradeItemId(), command.menge())
        );
    }

    @CommandHandler
    public void handle(RemoveTicketPositionCommand command) {
        AggregateLifeCycle.apply(
                new TicketPositionRemovedEvent(command.ticketId(),command.ticketPositionId(), command.tradeItemId(), command.menge())
        );

    }

    @CommandHandler
    public void handle(ConfirmTicketCommand command) {
        AggregateLifeCycle.apply(
                new TicketConfirmedEvent(command.ticketId())
        );
    }

}
