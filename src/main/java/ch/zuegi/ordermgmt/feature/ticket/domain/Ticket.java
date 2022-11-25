package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


public class Ticket extends AggregateRoot<Ticket, TicketId> {

    private LocalDateTime localDateTime;
    private TicketLifeCycleState ticketLifeCycleState;
    private Set<TicketPosition> ticketPositionSet;

    /*public Ticket(TicketNumber aggregateId) {
        super(aggregateId);

   *//*     LocalDateTime now = LocalDateTime.now();
        ticketEntity = new TicketEntity();
        ticketEntity.setTicketNumber(aggregateId);
        ticketEntity.setLocalDateTime(now);
        ticketEntity.setLifeCycleState(TicketLifeCycleState.TICKET_CREATED);

        TicketCreated ticketCreated = TicketCreated.eventOf(aggregateId, now, TicketLifeCycleState.TICKET_CREATED);
        this.validate();
        DomainEventPublisher
                .instance()
                .publish(ticketCreated);*//*

    }*/

    private Ticket(TicketId ticketId) {
        super(ticketId);
    }

    public static Ticket createTicket(TicketId ticketId, CreateTicketCommand command) {
        Ticket ticket = new Ticket(ticketId);

        if (command == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_COMMAND_MUST_NOT_BE_NULL);
        }

        ticket.localDateTime = command.getLocalDateTime();
        ticket.ticketLifeCycleState = command.getTicketLifeCycleState();

        ticket.ticketPositionSet = command.getCreateTicketPositionCommands()
                .stream()
                .map(createTicketPositionCommand -> TicketPosition.createTicketPosition(new TicketPositionId(), ticket.id(), createTicketPositionCommand))
                .collect(Collectors.toUnmodifiableSet());

        ticket.validate();


        TicketCreated ticketCreated = TicketCreated.builder()
                .ticketNumber(ticket.id())
                .lifeCycleState(ticket.ticketLifeCycleState)
                .localDateTime(ticket.localDateTime)
                .ticketPositionNumberSet(ticket.ticketPositionSet.stream().map(TicketPosition::id).collect(Collectors.toSet()))
                .build();


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
    }

    private void validateLifecycleState(TicketLifeCycleState toBeProvenTicketLifecycleState) {
        this.ticketLifeCycleState.next().orElseThrow(() -> new AggregateRootValidationException(AggregateRootValidationMsg.CURRENT_AGGREGATE_LIFECYCLE_STATE_IS_FINAL));

        if (!this.ticketLifeCycleState.equals(TicketLifeCycleState.TICKET_CREATED) && !this.ticketLifeCycleState.next().get().equals(toBeProvenTicketLifecycleState)) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_LIFECYCLE_STATE_NOT_ALLOWED);
        }
    }


}
