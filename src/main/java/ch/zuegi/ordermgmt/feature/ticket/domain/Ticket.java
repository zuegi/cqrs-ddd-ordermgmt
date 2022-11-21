package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveTicketCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.SaveTicketHandlerImpl;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;


public class Ticket extends AggregateRoot<Ticket, TicketNumber> {

    public Ticket(TicketNumber aggregateId) {
        super(aggregateId);
    }

    @Override
    protected AggregateRootBehavior<TicketNumber> initialBehavior() {
        AggregateRootBehaviorBuilder<TicketNumber> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(SaveTicketCommand.class, new SaveTicketHandlerImpl());
        return behaviorBuilder.build();    }

    @Override
    public TicketNumber id() {
        return this.aggregateId;
    }

}
