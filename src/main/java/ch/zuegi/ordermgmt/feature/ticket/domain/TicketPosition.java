package ch.zuegi.ordermgmt.feature.ticket.domain;

import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TicketPositionNumber;
import ch.zuegi.ordermgmt.feature.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRoot;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionNumber> {

    private TicketNumber ticketNumber;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    public TicketPosition(TicketPositionNumber aggregateId, TicketNumber ticketNumber, TradeItemId tradeItemId, BigDecimal menge) {
        super(aggregateId);
        this.ticketNumber = ticketNumber;
        this.tradeItemId = tradeItemId;
        this.menge = menge;
    }

    @Override
    public TicketPositionNumber id() {
        return this.aggregateId;
    }

    @Override
    protected void validate() {
        if (aggregateId == null) {
            throw new AggregateRootValidationException(AggregateRootValidationMsg.AGGREGATE_ID_MUST_NOT_BE_NULL);
        }
        if (!aggregateId.id.startsWith(aggregateId.getPrefix())) {
            throw new TicketPositionIdStartWithException("TicketId must have a leading \"" +aggregateId.getPrefix() +"\"");
        }
    }
}
