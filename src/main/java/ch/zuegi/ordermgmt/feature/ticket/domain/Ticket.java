package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
public class Ticket extends AggregateRoot<Ticket, TicketId> {

    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private Set<TicketPosition> ticketPositionSet;

    private Ticket(TicketId ticketId) {
        super(ticketId);
    }

    public static Ticket create(TicketId ticketId, CreateTicketCommand command) {
        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_COMMAND_MUST_NOT_BE_NULL);
        }

        Ticket ticket = new Ticket(ticketId);
        ticket.localDateTime = command.getLocalDateTime();
        ticket.ticketLifeCycleState = command.getTicketLifeCycleState();

        ticket.ticketPositionSet = command.getCreateTicketPositionCommands()
                .stream()
                .map(createTicketPositionCommand -> TicketPosition.createTicketPosition(new TicketPositionId(), ticket.id(), createTicketPositionCommand))
                .collect(Collectors.toUnmodifiableSet());

        ticket.validate();

        TicketCreated ticketCreated = TicketEventBuilder.build(ticket, TicketCreated.builder());

        DomainEventPublisher
                .instance()
                .publish(ticketCreated);

        return ticket;
    }

    @Override
    protected void validate() {
        // TODO Validations


    }

    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    public void updateState(TicketLifeCycleState ticketLifeCycleState) {
        this.validateLifecycleState(ticketLifeCycleState);
        this.ticketLifeCycleState = ticketLifeCycleState;

        TicketLifecycleUpdated lifecycleUpdated = TicketEventBuilder.build(this, TicketLifecycleUpdated.builder());
        DomainEventPublisher.instance().
                publish(lifecycleUpdated);
    }

    private void validateLifecycleState(TicketLifeCycleState toBeProvenTicketLifecycleState) {
        this.ticketLifeCycleState.next().orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL));

        if (!this.ticketLifeCycleState.equals(TicketLifeCycleState.TICKET_CREATED) && !this.ticketLifeCycleState.next().get().equals(toBeProvenTicketLifecycleState)) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_LIFECYCLE_STATE_NOT_ALLOWED);
        }
    }


}
