package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.*;

public class Ticket extends AggregateRoot<Ticket, TicketId> {

    private Ticket(TicketId aggregateID) {
        super(aggregateID);
    }

    public static Ticket create(TicketId orderTicketId, CreateTicket command) {
        Ticket orderTicket = new Ticket(orderTicketId);
        // CreateOrderTicketCommandHandler.handleCommand
        orderTicket.handleCommand(command);
        orderTicket.validate();
        return orderTicket;
    }

    @Override
    protected void validate() {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
    }

    @Override
    protected AggregateRootBehavior<TicketId> initialBehavior() {
        AggregateRootBehaviorBuilder<TicketId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(CreateTicket.class, new CreateTicketCommandHandler());

        return behaviorBuilder.build();
    }


    @Override
    public boolean sameIdentityAs(Ticket other) {
        return false;
    }

    @Override
    public TicketId id() {
        return null;
    }


}
