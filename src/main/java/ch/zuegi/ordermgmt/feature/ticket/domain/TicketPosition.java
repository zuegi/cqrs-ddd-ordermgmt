package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreatedEvent;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionId> {

    private TicketId ticketId;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    private TicketPosition(TicketPositionId aggregateID) {
        super(aggregateID);
    }


    public static TicketPosition create(TicketPositionId ticketPositionId, TicketId ticketId, CreateTicketPositionCommand command) {
        TicketPosition ticketPosition = new TicketPosition(ticketPositionId);
//        ticketPosition.validate(ticketPosition, command);
        ticketPosition.ticketId = ticketId;
        ticketPosition.tradeItemId = command.getTradeItemId();
        ticketPosition.menge = command.getMenge();

        TicketPositionCreatedEvent ticketPositionCreated = TicketEventBuilder.build(ticketPosition, TicketPositionCreatedEvent.builder());

        return ticketPosition;
    }

    @Override
    public TicketPositionId id() {
        return this.aggregateId;
    }

}
