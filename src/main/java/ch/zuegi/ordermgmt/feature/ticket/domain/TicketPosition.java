package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;

import java.math.BigDecimal;

public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionId> {

    private TicketId ticketId;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    private TicketPosition(TicketPositionId aggregateID) {
        super(aggregateID);
    }


    public static TicketPosition createTicketPosition(TicketPositionId ticketPositionId, TicketId ticketId, CreateTicketPositionCommand command) {
        TicketPosition ticketPosition = new TicketPosition(ticketPositionId);
        ticketPosition.ticketId = ticketId;
        ticketPosition.tradeItemId = command.getTradeItemId();
        ticketPosition.menge = command.getMenge();
        ticketPosition.validate();

        return ticketPosition;
    }

    @Override
    public TicketPositionId id() {
        return this.aggregateId;
    }

    @Override
    protected void validate() {

    }
}
