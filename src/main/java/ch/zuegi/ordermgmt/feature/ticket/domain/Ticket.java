package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehavior;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootBehaviorBuilder;

public class Ticket extends AggregateRoot<Ticket, TicketId> {

    private Ticket(TicketId aggregateID) {
        super(aggregateID);
    }

    public static Ticket create(TicketId orderTicketId) {
        Ticket orderTicket = new Ticket(orderTicketId);
        // FIXME Implementierung
        return orderTicket;
    }


    @Override
    protected AggregateRootBehavior<TicketId> initialBehavior() {
        AggregateRootBehaviorBuilder<TicketId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(CreateTicket.class, new CreateTicketCommandHandler());

        return behaviorBuilder.build();
    }

    @Override
    public TicketId id() {
        return null;
    }


}
