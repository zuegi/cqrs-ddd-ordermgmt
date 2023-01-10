package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.AddTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionId> {

    private TicketId ticketId;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    private TicketPosition(TicketPositionId aggregateID) {
        super(aggregateID);
    }


    public static TicketPosition create(TicketPositionId ticketPositionId,  AddTicketPositionCommand command) {
        TicketPosition ticketPosition = new TicketPosition(ticketPositionId);
        ticketPosition.ticketId = command.getTicketId();
        ticketPosition.tradeItemId = command.getTradeItemId();
        ticketPosition.menge = command.getMenge();

        return ticketPosition;
    }

    @Override
    public TicketPositionId id() {
        return this.aggregateId;
    }

}
