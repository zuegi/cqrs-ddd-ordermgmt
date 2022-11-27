package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.CreateTicketCommandPositionValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;

import java.math.BigDecimal;
import java.util.Map;

public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionId> {

    private TicketId ticketId;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    private TicketPosition(TicketPositionId aggregateID) {
        super(aggregateID);
    }

    @Override
    protected AggregateRootValidators initialValidators() {
        return AggregateRootValidators.builder()
                .validators(
                        Map.of(CreateTicketPositionCommand.class, new CreateTicketCommandPositionValidator()))
                .build();
    }


    public static TicketPosition create(TicketPositionId ticketPositionId, TicketId ticketId, CreateTicketPositionCommand command) {
        TicketPosition ticketPosition = new TicketPosition(ticketPositionId);
        ticketPosition.validate(command);
        ticketPosition.ticketId = ticketId;
        ticketPosition.tradeItemId = command.getTradeItemId();
        ticketPosition.menge = command.getMenge();

        return ticketPosition;
    }

    @Override
    public TicketPositionId id() {
        return this.aggregateId;
    }

}
