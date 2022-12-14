package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.command.CreateTicketPositionCommand;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketEventBuilder;
import ch.zuegi.ordermgmt.feature.ticket.domain.event.TicketPositionCreated;
import ch.zuegi.ordermgmt.feature.ticket.domain.validator.CreateTicketCommandPositionValidator;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
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
        ticketPosition.validate(ticketPosition, command);
        ticketPosition.ticketId = ticketId;
        ticketPosition.tradeItemId = command.getTradeItemId();
        ticketPosition.menge = command.getMenge();

        TicketPositionCreated ticketPositionCreated = TicketEventBuilder.build(ticketPosition, TicketPositionCreated.builder());

        DomainEventPublisher
                .instance()
                .publish(ticketPositionCreated);
        return ticketPosition;
    }

    @Override
    public TicketPositionId id() {
        return this.aggregateId;
    }

}
