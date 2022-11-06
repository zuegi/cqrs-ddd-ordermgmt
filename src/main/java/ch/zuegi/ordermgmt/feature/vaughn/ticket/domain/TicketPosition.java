package ch.zuegi.ordermgmt.feature.vaughn.ticket.domain;

import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.shared.Entity;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TicketPositionId;
import ch.zuegi.ordermgmt.feature.vaughn.ticket.domain.vo.TradeItemId;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TicketPosition extends Entity<TicketPositionId> {

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
}
