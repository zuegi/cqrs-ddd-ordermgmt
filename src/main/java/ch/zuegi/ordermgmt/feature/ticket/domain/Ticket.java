package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.*;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketPosition;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketConfirmedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionAddedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionRemovedEvent;
import ch.zuegi.ordermgmt.shared.annotation.Aggregate;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.gateway.command.AggregateLifeCycle;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@ToString
@Aggregate
public class Ticket {

    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private List<TicketPosition> ticketPositionList;

    @CommandHandler
    public void handle(UpdateTicketLifecycleCommand updateTicketLifecycleCommand) {
        this.ticketLifeCycleState = updateTicketLifecycleCommand.getTicketLifeCycleState();
        this.localDateTime = updateTicketLifecycleCommand.getLocalDateTime();

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
