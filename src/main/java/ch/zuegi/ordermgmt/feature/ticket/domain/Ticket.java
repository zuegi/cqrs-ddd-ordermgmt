package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import ch.zuegi.ordermgmt.shared.annotation.CommandHandler;
import ch.zuegi.ordermgmt.shared.annotation.CommandValidator;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@ToString
public class Ticket extends AggregateRoot<Ticket, TicketId>  {

    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private List<TicketPosition> ticketPositionList;

    public Ticket(TicketId ticketId) {
        super(ticketId);
    }


    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    @CommandHandler
    public TicketLifecycleUpdatedEvent handle(UpdateTicketLifecycleCommand updateTicketLifecycleCommand) {
        this.ticketLifeCycleState = updateTicketLifecycleCommand.getTicketLifeCycleState();
        this.localDateTime = updateTicketLifecycleCommand.getLocalDateTime();

        return TicketEventBuilder.build(this, TicketLifecycleUpdatedEvent.builder());
    }

    @CommandHandler
    public TicketCreatedEvent handle(CreateTicketCommand createTicketCommand) {
        this.localDateTime = createTicketCommand.getLocalDateTime();
        this.ticketLifeCycleState = createTicketCommand.getTicketLifeCycleState();

        return TicketEventBuilder.build(this, TicketCreatedEvent.builder());
    }

    @CommandHandler
    public TicketPositionCreatedEvent handle(AddTicketPositionCommand addTicketPosition) {
        TicketPosition ticketPosition = TicketPosition.create(new TicketPositionId(), addTicketPosition);
        if (this.ticketPositionList == null) {
            this.ticketPositionList = new ArrayList<>();
        }
        this.ticketPositionList.add(ticketPosition);

        return TicketEventBuilder.build(ticketPosition, TicketPositionCreatedEvent.builder());
    }

    @CommandValidator
    public void validate(CreateTicketCommand command) {

        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }

    }

    @CommandValidator
    public void validate(UpdateTicketLifecycleCommand command) {
        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }

        if (command.getTicketLifeCycleState() == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);
        }

        this.getTicketLifeCycleState().next().orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL));

        if (!this.getTicketLifeCycleState().equals(TicketLifeCycleState.TICKET_CREATED) && !this.getTicketLifeCycleState().next().get().equals(command.getTicketLifeCycleState())) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_LIFECYCLE_STATE_NOT_ALLOWED);
        }
    }

    @CommandValidator
    public void validate(AddTicketPositionCommand addTicketPosition) {
        if (addTicketPosition == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.ADD_TICKET_POSITION_COMMAND_MUST_NOT_BE_EMTPY);
        }
        if (!this.id().equals(addTicketPosition.getTicketId())) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.WRONG_TICKET_FOR_TICKET_POSITION);
        }
    }


    public void aggregateEvent(TicketCreatedEvent ticketCreatedEvent) {
        // allenfalls den event validieren
        this.localDateTime = ticketCreatedEvent.getLocalDateTime();
        this.ticketLifeCycleState = ticketCreatedEvent.getLifeCycleState();
    }

    public void aggregateTicketPositionEvents(List<TicketPositionCreatedEvent> eventList) {
        // to be implemented
    }

}
