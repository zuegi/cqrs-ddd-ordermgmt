package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.CreateTicketCommandValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.TicketDomainValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.UpdateTicketLifefycleValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainHandler;
import ch.zuegi.ordermgmt.shared.DomainValidator;
import ch.zuegi.ordermgmt.shared.OldDomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class Ticket extends AggregateRoot<Ticket, TicketId>  {

    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private Set<TicketPosition> ticketPositionSet;

    private TicketDomainValidator ticketValidator;

    public Ticket(TicketId ticketId) {
        super(ticketId);
        ticketValidator = new TicketDomainValidator(this);
    }


    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    @DomainHandler
    public void handle(UpdateTicketLifecycleCommand updateTicketLifecycleCommand) throws InvocationTargetException, IllegalAccessException {
        ticketValidator.validate(updateTicketLifecycleCommand);
        this.ticketLifeCycleState = updateTicketLifecycleCommand.getTicketLifeCycleState();
        this.localDateTime = updateTicketLifecycleCommand.getLocalDateTime();

    }


    // Erstelle eine CommandHandler Annotation und die Registrierung des entsprechenden CommandHandlers
    @DomainHandler
    public void handle(CreateTicketCommand createTicketCommand) throws InvocationTargetException, IllegalAccessException {
        ticketValidator.validate(createTicketCommand);
        this.localDateTime = createTicketCommand.getLocalDateTime();
        this.ticketLifeCycleState = createTicketCommand.getTicketLifeCycleState();

        // oder den fokus gar nicht auf dem Ticket, sondern vielmehr auf der TicketPosition?
        this.ticketPositionSet = createTicketCommand.getCreateTicketPositionCommands()
                .stream()
                .map(createTicketPositionCommand -> TicketPosition.create(new TicketPositionId(), this.id(), createTicketPositionCommand))
                .collect(Collectors.toUnmodifiableSet());

    }

    @DomainValidator
    public void validate(CreateTicketCommand command) {

        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }

        if (command.getCreateTicketPositionCommands() == null || command.getCreateTicketPositionCommands().isEmpty()) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_POSITION_SET_MUST_NOT_BE_EMPTY);
        }
    }

    @DomainValidator
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
}
