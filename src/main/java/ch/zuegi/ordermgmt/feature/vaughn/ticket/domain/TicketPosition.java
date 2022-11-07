package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.AggregateRoot;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.Entity;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationException;
import ch.zuegi.ordermgmt.shared.aggregateRoot.AggregateRootValidationMsg;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketPosition extends AggregateRoot<TicketPosition, TicketPositionId> {

    private TicketId ticketId;
    private TradeItemId tradeItemId;
    private BigDecimal menge;

    public TicketPosition(TicketPositionId aggregateId,TicketId ticketId, TradeItemId tradeItemId, BigDecimal menge) {
        super(aggregateId);
        this.ticketId = ticketId;
        this.tradeItemId = tradeItemId;
        this.menge = menge;
    }

    @Override
    public TicketPositionId id() {
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
