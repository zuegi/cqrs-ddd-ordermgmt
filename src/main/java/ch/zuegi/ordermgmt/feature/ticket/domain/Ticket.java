package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.UpdateTicketLifecycleCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.entity.TicketLifeCycleState;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketLifecycleUpdated;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.CreateTicketCommandValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.UpdateTicketLifefycleValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
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

    @Override
    protected AggregateRootValidators initialValidators() {
        return AggregateRootValidators.builder()
                .validators(
                        Map.of(
                                CreateTicketCommand.class, new CreateTicketCommandValidator(),
                                UpdateTicketLifecycleCommand.class, new UpdateTicketLifefycleValidator()

                        )
                )
                .build();
    }


    public static Ticket create(TicketId ticketId, CreateTicketCommand command) {
        Ticket ticket = new Ticket(ticketId);
        ticket.validate(ticket, command);
        ticket.localDateTime = command.getLocalDateTime();
        ticket.ticketLifeCycleState = command.getTicketLifeCycleState();

        // TODO kann ich hier einen schöneren Ansatz fahren
        // extract CreateTicketPosition
        // erstelle TicketPosition
        // füge TicketPosition hinzu

        // oder den fokus gar nicht auf dem Ticket, sondern vielmehr auf der TicketPosition?
        ticket.ticketPositionSet = command.getCreateTicketPositionCommands()
                .stream()
                .map(createTicketPositionCommand -> TicketPosition.create(new TicketPositionId(), ticket.id(), createTicketPositionCommand))
                .collect(Collectors.toUnmodifiableSet());


        TicketCreated ticketCreated = TicketEventBuilder.build(ticket, TicketCreated.builder());

        DomainEventPublisher
                .instance()
                .publish(ticketCreated);

        return ticket;
    }




    @Override
    public TicketId id() {
        return this.aggregateId;
    }

    public void updateState(UpdateTicketLifecycleCommand updateTicketLifecycleCommand) {
        this.validate(this, updateTicketLifecycleCommand);
        this.ticketLifeCycleState = updateTicketLifecycleCommand.getTicketLifeCycleState();
        this.localDateTime = updateTicketLifecycleCommand.getLocalDateTime();

        TicketLifecycleUpdated lifecycleUpdated = TicketEventBuilder.build(this, TicketLifecycleUpdated.builder());
        DomainEventPublisher.instance().
                publish(lifecycleUpdated);
    }


}
