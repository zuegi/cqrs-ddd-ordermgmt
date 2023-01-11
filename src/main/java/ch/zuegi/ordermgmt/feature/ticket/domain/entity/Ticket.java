package ch.zuegi.ordermgmt.feature.ticket.domain.entity;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.*;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.*;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainEvent;
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
import java.util.Optional;
import java.util.function.Predicate;


@Getter
@ToString
public class Ticket extends AggregateRoot<Ticket, TicketId> {

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
    public TicketPositionAddedEvent handle(AddTicketPositionCommand addTicketPosition) {
        TicketPosition ticketPosition = TicketPosition.create(new TicketPositionId(), addTicketPosition);
        if (this.ticketPositionList == null) {
            this.ticketPositionList = new ArrayList<>();
        }
        this.ticketPositionList.add(ticketPosition);

        return TicketEventBuilder.build(ticketPosition, TicketPositionAddedEvent.builder());
    }

    @CommandHandler
    public TicketPositionRemovedEvent handle(RemoveTicketPositionCommand removeTicketPositionCommand) {
        if (this.ticketPositionList == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_POSITION_LIST_MUST_NOT_BE_NULL_WHEN_REMOVING_TICKET_POSITION);
        }
        TicketPosition ticketPosition = ticketPositionList.stream()
                .filter(tp -> tp.id().equals(removeTicketPositionCommand.getTicketPositionId()))
                .findAny()
                .orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_POSITION_ID_NOT_FOUND));
        this.ticketPositionList.remove(ticketPosition);

        return TicketEventBuilder.build(ticketPosition, TicketPositionRemovedEvent.builder());
    }

    @CommandHandler
    public TicketConfirmedEvent handle(ConfirmTicketCommand command) {
        this.ticketLifeCycleState = TicketLifeCycleState.TICKET_CONFIRMED;
        return TicketEventBuilder.build(this, TicketConfirmedEvent.builder());
    }


    @CommandValidator
    public void validate(ConfirmTicketCommand command) {
        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }

        if (!command.getTicketId().sameValueAs(this.id())) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_TICKET_ID_NOT_THE_SAME);
        }
    }

    @CommandValidator
    public void validate(RemoveTicketPositionCommand removeTicketPositionCommand) {
        if (removeTicketPositionCommand == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.TICKET_COMMAND_MUST_NOT_BE_EMPTY);
        }
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
        if (!this.id().sameValueAs(addTicketPosition.getTicketId())) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.WRONG_TICKET_FOR_TICKET_POSITION);
        }
    }

    public void aggregateEvents(List<DomainEvent<?, TicketId>> ticketDomainEvents) {
        // TODO Validation?
        Optional<DomainEvent<?, TicketId>> ticketIdDomainEvent = extractTicketCreatedEvent(ticketDomainEvents);

        if (ticketIdDomainEvent.isPresent()) {
            TicketCreatedEvent ticketCreatedEvent = (TicketCreatedEvent) ticketIdDomainEvent.get();
            aggregateEvent(ticketCreatedEvent);

            aggregateTicketPositionAddedEvent(ticketDomainEvents);
            aggregateTicketPositionRemovedEvent(ticketDomainEvents);
            aggreateTicketConfirmedEvents(ticketDomainEvents);
        }


    }

    private void aggreateTicketConfirmedEvents(List<DomainEvent<?, TicketId>> ticketDomainEvents) {
        ticketDomainEvents.stream()
                .filter(event -> event.id().sameValueAs(this.id()))
                .filter(event -> event instanceof TicketConfirmedEvent)
                .map(event -> (TicketConfirmedEvent) event)
                .findAny()
                .ifPresent(ticketConfirmedEvent -> this.ticketLifeCycleState = ticketConfirmedEvent.getTicketLifeCycleState());

    }

    private void aggregateTicketPositionRemovedEvent(List<DomainEvent<?, TicketId>> ticketDomainEvents) {
        ticketDomainEvents.stream()
                .filter(event -> event.id().sameValueAs(this.id()))
                .filter(event -> event instanceof TicketPositionRemovedEvent)
                .map(event -> (TicketPositionRemovedEvent) event)
                .forEach(this::removeFromPositionList);
    }

    private void removeFromPositionList(TicketPositionRemovedEvent event) {
        ticketPositionList.removeIf(getTicketPositionPredicate(event));
    }

    private static Predicate<TicketPosition> getTicketPositionPredicate(TicketPositionRemovedEvent event) {
        return pos -> pos.id().sameValueAs(event.getTicketPositionId());
    }


    private Optional<DomainEvent<?, TicketId>> extractTicketCreatedEvent(List<DomainEvent<?, TicketId>> ticketDomainEvents) {
        return ticketDomainEvents.stream()
                .filter(event -> event instanceof TicketCreatedEvent)
                .filter(event -> ((TicketCreatedEvent) event).getTicketId().sameValueAs(this.id()))
                .findAny();
    }

    private void aggregateEvent(TicketCreatedEvent ticketCreatedEvent) {
        // allenfalls den event validieren
        this.localDateTime = ticketCreatedEvent.getLocalDateTime();
        this.ticketLifeCycleState = ticketCreatedEvent.getLifeCycleState();
    }

    private void aggregateTicketPositionAddedEvent(List<DomainEvent<?, TicketId>> ticketDomainEvents) {
        // to be implemented
        List<TicketPosition> ticketPositions = ticketDomainEvents.stream()
                .filter(event -> event.id().sameValueAs(this.id()))
                .filter(event -> event instanceof TicketPositionAddedEvent)
                .map(event -> (TicketPositionAddedEvent) event)
                .map(this::eventToPositionAddedMapper)
                .toList();
        if (this.ticketPositionList == null) {
            this.ticketPositionList = new ArrayList<>();
        }
        this.ticketPositionList.addAll(ticketPositions);
    }

    private TicketPosition eventToPositionAddedMapper(TicketPositionAddedEvent event) {

        AddTicketPositionCommand command = AddTicketPositionCommand.builder()
                .ticketId(event.getTicketId())
                .ticketPositionId(event.getTicketPositionId())
                .tradeItemId(event.getTradeItemId())
                .menge(event.getMenge())
                .build();

        return TicketPosition.create(event.getTicketPositionId(), command);
    }

}