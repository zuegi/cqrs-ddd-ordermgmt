package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderTicket;
import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateOrderTicketCommandHandler;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.OrderTicketId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.*;

public class OrderTicket extends AggregateRoot<OrderTicket, OrderTicketId> {

    private OrderTicket(OrderTicketId aggregateID) {
        super(aggregateID);
    }

    public static OrderTicket create(OrderTicketId orderTicketId, CreateOrderTicket command) {
        OrderTicket orderTicket = new OrderTicket(orderTicketId);
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
    protected AggregateRootBehavior<OrderTicketId> initialBehavior() {
        AggregateRootBehaviorBuilder<OrderTicketId> behaviorBuilder = new AggregateRootBehaviorBuilder<>();
        behaviorBuilder.setCommandHandler(CreateOrderTicket.class, new CreateOrderTicketCommandHandler());

        return behaviorBuilder.build();
    }


    @Override
    public boolean sameIdentityAs(OrderTicket other) {
        return false;
    }

    @Override
    public OrderTicketId id() {
        return null;
    }


}
