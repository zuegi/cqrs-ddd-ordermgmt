package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.DomainEventPublisher;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class Ticket extends AggregateRoot<Ticket, TicketId> {

    Set<TicketPosition> ticketPositionSet;

    public Ticket(TicketId aggregateId) {
        super(aggregateId);

        this.validate();
        this.ticketPositionSet = new HashSet<>();

        TicketCreated ticketCreated = TicketCreated.eventOf(aggregateId, LocalDateTime.now());
        DomainEventPublisher
                .instance()
                .publish(ticketCreated);
    }

    @Override
    protected void validate() {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
        if (!aggregateId.id.startsWith(aggregateId.getPrefix())) {
            throw new TicketIdStartWithException("TicketId must have a leading \"" +aggregateId.getPrefix() +"\"");
        }
    }

    @Override
    public TicketId id() {
        return this.aggregateId;
    }


    public TicketPosition addTicketPosition(TicketPositionId ticketPositionId, TicketId ticketId, TradeItemId tradeItemId, BigDecimal menge) {
        TicketPosition ticketPosition = new TicketPosition(ticketPositionId, ticketId, tradeItemId, menge);
        this.ticketPositionSet.add(ticketPosition);
        //add TicketPositionAdded in DomainPublisher
        TicketPositionAdded ticketPositionAdded = TicketPositionAdded.eventOf(ticketPosition.id(), ticketPosition.getTicketId(), ticketPosition.getTradeItemId(), ticketPosition.getMenge());
        DomainEventPublisher
                .instance()
                .publish(ticketPositionAdded);

        return ticketPosition;
    }
}
